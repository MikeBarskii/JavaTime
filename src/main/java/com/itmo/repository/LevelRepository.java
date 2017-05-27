package com.itmo.repository;

import com.itmo.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("levelRepository")
public interface LevelRepository extends JpaRepository<Level, Integer> {
    Level findByLevel(String level);

    @Override
    List<Level> findAll();
}
