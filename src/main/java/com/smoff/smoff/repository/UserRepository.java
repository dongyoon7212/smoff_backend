package com.smoff.smoff.repository;

import com.smoff.smoff.entity.User;
import com.smoff.smoff.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    //====================== Auth =======================
    public Optional<User> addUser(User user) {
        try {
            userMapper.addUser(user);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> getUserByUserId(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public int softDeleteUser(Integer userId) {
        return userMapper.softDeleteUser(userId);
    }

    //====================== Account =======================
    public int changePassword(User user) {
        return userMapper.changePassword(user);
    }

    public int changeUsername(User user) {
        return userMapper.changeUsername(user);
    }

    public int changeProfileImg(User user) {
        return userMapper.changeProfileImg(user);
    }
}
