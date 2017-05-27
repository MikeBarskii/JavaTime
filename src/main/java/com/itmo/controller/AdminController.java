package com.itmo.controller;

import com.itmo.model.Competition;
import com.itmo.model.Level;
import com.itmo.model.Task;
import com.itmo.model.User;
import com.itmo.service.CompetitionService;
import com.itmo.service.LevelService;
import com.itmo.service.TaskService;
import com.itmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private CompetitionService competitionService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.findUsersByActive(1);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/users_list");
        return modelAndView;
    }

    @RequestMapping(value = "/admin_competitions", method = RequestMethod.GET)
    public ModelAndView competitionsPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Competition> competitions = competitionService.findAllCompetitions();
        modelAndView.addObject("competitions", competitions);
        modelAndView.setViewName("admin/competitions");
        return modelAndView;
    }

    @RequestMapping(value = "/competition_add", method = RequestMethod.GET)
    public ModelAndView addCompetitionPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> allUsers = userService.findAllUsers();
        List<Task> elementaryTasks = findTasksByLevel("elementary");
        List<Task> averageTasks = findTasksByLevel("average");
        List<Task> heavyTasks = findTasksByLevel("heavy");
        modelAndView.addObject("allUsers", allUsers);
        modelAndView.addObject("elementary_tasks", elementaryTasks);
        modelAndView.addObject("average_tasks", averageTasks);
        modelAndView.addObject("heavy_tasks", heavyTasks);
        modelAndView.addObject("competition", new Competition());
        modelAndView.setViewName("admin/competition_add");
        return modelAndView;
    }

    @RequestMapping(value = "/competition_add", method = RequestMethod.POST)
    public ModelAndView addCompetition(Competition competition, BindingResult bindingResult) {
        int participants = competition.getUsers().size();
        competition.setParticipants(participants);

        competitionService.saveCompetition(competition);
        return new ModelAndView("redirect:admin_competitions");
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

    private List<Task> findTasksByLevel(String levelName) {
        Level level = levelService.findLevelByName(levelName.toLowerCase());
        return taskService.findAllByLevel(level);
    }
}
