package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.mail.SendMailReqDto;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.entity.UserRole;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.repository.UserRoleRepository;
import com.smoff.smoff.security.jwt.JwtUtils;
import com.smoff.smoff.security.model.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public ApiRespDto<?> sendMail(SendMailReqDto sendMailReqDto) {

        Optional<User> optionalUser = userRepository.getUserByEmail(sendMailReqDto.getEmail());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 이메일 입니다.", null);
        }

        User user = optionalUser.get();

        boolean hasTempRole = user.getUserRoles().stream().anyMatch(userRole -> userRole.getRoleId() == 3);

        if (!hasTempRole) {
            return new ApiRespDto<>("failed", "인증이 필요한 계정이 아닙니다.", null);
        }

        String verifyToken = jwtUtils.generateMailVerifyToken(user.getUserId().toString());

//        SimpleMailMessage message = new SimpleMailMessage();
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // HTML/한글 안전
            helper.setTo(user.getEmail());
            helper.setFrom("no-reply@smoff.com", "Smoff");      // 발신명까지(선택)
            helper.setReplyTo("support@smoff.com");             // 회신 주소(선택)
            helper.setSubject("[Smoff] 이메일 인증 코드입니다.");


            String html = getString(verifyToken);

            helper.setText(html, true); // ← HTML 본문
            javaMailSender.send(message);
        } catch (Exception e) {
            // TODO: 로깅/알림
        }

        return new ApiRespDto<>("success", "인증 메일을 전송하였습니다. 이메일을 확인해주세요.", null);
    }

    private static String getString(String verifyToken) {
        String verifyUrl = "http://localhost:8080/mail/verify?verifyToken=" + verifyToken;

        String html =
                "<div style=\"font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;" +
                        "background:#f7f8fa;padding:30px;border-radius:12px;border:1px solid #e5e7eb;max-width:520px;margin:auto;\">" +

                        "<div style=\"text-align:center;margin-bottom:20px;\">" +
                        "<img src='https://.../logo.png' alt='Smoff' style='height:64px;display:block;margin:0 auto;'/>" +
                        "</div>" +

                        "<h2 style=\"color:#111827;text-align:center;margin:0 0 8px;\">이메일 인증</h2>" +
                        "<p style=\"font-size:14px;color:#374151;text-align:center;margin:0;\">아래 버튼을 눌러 이메일 인증을 완료해 주세요.</p>" +

                        // ✅ 테이블 기반 '버튼' (이메일 호환성 좋음)
                        "<table role='presentation' cellspacing='0' cellpadding='0' border='0' align='center' style='margin:24px auto;'>" +
                        "<tr>" +
                        "<td align='center' bgcolor='#111827' style='border-radius:10px;'>" +
                        "<a href='" + verifyUrl + "' " +
                        "style='display:inline-block;padding:12px 20px;font-weight:700;text-decoration:none;" +
                        "background:#111827;color:#ffffff;border-radius:10px;font-size:14px;'>" +
                        "인증 완료하기" +
                        "</a>" +
                        "</td>" +
                        "</tr>" +
                        "</table>" +

                        "<hr style=\"margin:24px 0;border:none;border-top:1px solid #e5e7eb;\"/>" +
                        "<p style=\"font-size:12px;color:#9ca3af;text-align:center;margin:0 0 6px;\">버튼이 동작하지 않으면 아래 링크를 복사해 브라우저에 붙여넣으세요.</p>" +
                        "<p style=\"font-size:12px;color:#6b7280;text-align:center;word-break:break-all;margin:0;\">" + verifyUrl + "</p>" +

                        "</div>";
        return html;
    }

    public Map<String, Object> verify(String token) {
        Claims claims = null;
        Map<String, Object> resultMap = null;

        try {
            claims = jwtUtils.getClaims(token);
            String subject = claims.getSubject();
            if(!"VerifyToken".equals(subject)) {
                resultMap = Map.of("status", "failed", "message", "잘못된 요청입니다.");
            }

            Integer userId = Integer.parseInt(claims.getId());
            Optional<User> optionalUser = userRepository.getUserByUserId(userId);
            if (optionalUser.isEmpty()) {
                resultMap = Map.of("status", "failed", "message", "존재하지 않는 사용자입니다.");
            }

            Optional<UserRole> optionalUserRole = userRoleRepository.getUserRoleByUserIdAndRoleId(userId, 3);
            if (optionalUserRole.isEmpty()) {
                resultMap = Map.of("status", "failed", "message", "이미 인증 완료된 메일입니다.");
            } else {
                userRoleRepository.updateRoleId(optionalUserRole.get().getUserRoleId(), userId);
                resultMap = Map.of("status", "success", "message", "이메일 인증이 완료되었습니다.");
            }
        } catch (ExpiredJwtException e) {
            resultMap = Map.of("status", "failed", "message", "인증 시간이 만료된 요청입니다. \n인증 메일을 다시 요청하세요.");
        } catch (Exception e) {
            resultMap = Map.of("status", "failed", "message", "잘못된 요청입니다.\n인증 메일을 다시 요청하세요.");
        }
        return resultMap;
    }
}
