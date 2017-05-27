package com.itmo.repository;

import com.itmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findUsersByActive(int active);

    @Override
    List<User> findAll();
}
