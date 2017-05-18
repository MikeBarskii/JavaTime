package com.itmo.service;

import com.itmo.model.User;

public interface UserService {
    public User findUserByEmail(String email);

    public void saveUser(User user);
}
