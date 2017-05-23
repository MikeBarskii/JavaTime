package com.itmo.service;

import com.itmo.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    void saveUser(User user);

    void modifyUser(User user);

    List<User> findUsersByActive(int active);
}
