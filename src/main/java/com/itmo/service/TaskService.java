package com.itmo.service;

import com.itmo.model.Task;

import java.util.List;

public interface TaskService {
    void saveTask(Task task, String level);

    List<Task> findAllTasks();
}
