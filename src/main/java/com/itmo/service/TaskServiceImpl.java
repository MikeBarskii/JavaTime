package com.itmo.service;

import com.itmo.model.Level;
import com.itmo.model.Task;
import com.itmo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LevelService levelService;

    @Override
    public void saveTask(Task task, String level) {
        Level taskLevel = levelService.findLevelByName(level);
        task.setLevels(new HashSet<>(Arrays.asList(taskLevel)));
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByLevel(Level level) {
        return taskRepository.findAllByLevelsContains(level);
    }
}
