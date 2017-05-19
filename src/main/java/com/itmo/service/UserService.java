package com.itmo.service;

import com.itmo.model.User;

public interface UserService {
    User findUserByEmail(String email);

    void saveUser(User user);

    void modifyUser(User user);
}
