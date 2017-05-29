package com.itmo.service;

import com.itmo.model.Role;
import com.itmo.model.User;
import com.itmo.repository.RoleRepository;
import com.itmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void modifyUser(User user) {
        User userDB = userService.findUserByEmail(user.getEmail());
        Arrays.stream(User.class.getDeclaredFields()).forEach(f -> mergeUserByField(userDB, user, f.getName()));
        userRepository.save(userDB);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public List<User> findUsersByActive(boolean active) {
        return userRepository.findUsersByActive(active);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    private void mergeUserByField(User userDb, User userReq, String field) {
        switch (field) {
            case "name":
                if (userDb.getName() == null || !userDb.getName().equals(userReq.getName()))
                    userDb.setName(userReq.getName());
                break;
            case "lastname":
                if (userDb.getLastName() == null || !userDb.getLastName().equals(userReq.getLastName()))
                    userDb.setLastName(userReq.getLastName());
                break;
            case "email":
                if (userDb.getEmail() == null || !userDb.getEmail().equals(userReq.getEmail()))
                    userDb.setEmail(userReq.getEmail());
                break;
            case "company":
                if (userDb.getCompany() == null || !userDb.getCompany().equals(userReq.getCompany()))
                    userDb.setCompany(userReq.getCompany());
                break;
        }
    }

}
