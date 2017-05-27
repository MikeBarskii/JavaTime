package com.itmo.repository;

import com.itmo.model.Level;
import com.itmo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Override
    List<Task> findAll();

    List<Task> findAllByLevelsContains(Level level);
}
