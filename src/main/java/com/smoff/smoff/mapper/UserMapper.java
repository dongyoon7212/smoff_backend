package com.smoff.smoff.mapper;

import com.smoff.smoff.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    //====================== Auth =======================
    int addUser(User user);
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    int softDeleteUser(Integer userId);

    //====================== Account =======================
    int changePassword(User user);
    int changeUsername(User user);
}
