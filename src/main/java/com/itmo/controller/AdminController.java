package com.itmo.controller;

import com.itmo.model.Competition;
import com.itmo.model.Level;
import com.itmo.model.Task;
import com.itmo.model.User;
import com.itmo.service.LevelService;
import com.itmo.service.TaskService;
import com.itmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class AdminController extends ProjectController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private LevelService levelService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.findUsersByActive(1);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/users_list");
        return modelAndView;
    }

    @RequestMapping(value = "/competitions", method = RequestMethod.GET)
    public ModelAndView competitionsPage() {
        ModelAndView modelAndView = new ModelAndView();
//        List<User> users = userService.findUsersByActive(1);
//        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/competitions");
        return modelAndView;
    }

    @RequestMapping(value = "/competition_add", method = RequestMethod.GET)
    public ModelAndView addCompetitionPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Level> levels = levelService.findAllLevels();
        List<User> users = userService.findAllUsers();
        modelAndView.addObject("levels", levels);
        modelAndView.addObject("users", users);
        modelAndView.addObject("competition", new Competition());
        modelAndView.setViewName("admin/competition_add");
        return modelAndView;
    }

    @RequestMapping(value = "/competition_add", method = RequestMethod.POST)
    public ModelAndView addCompetition() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("competition", new Competition());
        return new ModelAndView("redirect:competitions");
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ModelAndView tasksPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Task> tasks = taskService.findAllTasks();
        modelAndView.addObject("tasks", tasks);
        modelAndView.setViewName("admin/tasks");
        return modelAndView;
    }

    @RequestMapping(value = "/task_add", method = RequestMethod.GET)
    public ModelAndView addTaskPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Level> levels = levelService.findAllLevels();
        modelAndView.addObject("levels", levels);
        modelAndView.addObject("task", new Task());
        modelAndView.addObject("level", new Level());
        modelAndView.setViewName("admin/task_add");
        return modelAndView;
    }

    @RequestMapping(value = "/task_add", method = RequestMethod.POST)
    public ModelAndView addTask(Task task, String level) {
        taskService.saveTask(task, level);
        return new ModelAndView("redirect:tasks");
    }
//    @RequestMapping(value = "/user_profile", method = RequestMethod.GET)
//    public ModelAndView userProfile(User user) {
//        ModelAndView modelAndView = new ModelAndView();
//        User userDB = userService.findUserByEmail(user.getEmail());
//        modelAndView.addObject("user", userDB);
//        modelAndView.setViewName("user/profile");
//        return modelAndView;
//    }

}
