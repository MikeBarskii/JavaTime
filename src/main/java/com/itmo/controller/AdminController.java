package com.itmo.controller;

import com.itmo.model.Competition;
import com.itmo.model.Level;
import com.itmo.model.Task;
import com.itmo.model.User;
import com.itmo.model.components.CompetitionUser;
import com.itmo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    @Autowired
    private CompetitionUserService competitionUserService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.findUsersByActive(true);

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
        List<User> activationUsers = new ArrayList<>();
        List<User> fufUsers = new ArrayList<>();
        List<User> dbUsers = new ArrayList<>();

        activationUsers.add(userService.findUserByEmail("tarassimon@mail.ru"));

        fufUsers.add(userService.findUserByEmail("marat.daishev@yandex.ru"));
        fufUsers.add(userService.findUserByEmail("evgeniy.gavrilov@mail.ru"));

        fufUsers.add(userService.findUserByEmail("dmitriy.moldovanov@mail.ru"));
        fufUsers.add(userService.findUserByEmail("olegpetrov@mail.ru"));

        dbUsers.add(userService.findUserByEmail("ivanov1239@mail.ru"));


        List<Task> elementaryTasks = findTasksByLevel("elementary");
        List<Task> averageTasks = findTasksByLevel("average");
        List<Task> heavyTasks = findTasksByLevel("heavy");

        modelAndView.addObject("fufUsers", fufUsers);
        modelAndView.addObject("activationUsers", activationUsers);
        modelAndView.addObject("dbUsers", dbUsers);

        modelAndView.addObject("elementary_tasks", elementaryTasks);
        modelAndView.addObject("average_tasks", averageTasks);
        modelAndView.addObject("heavy_tasks", heavyTasks);
        modelAndView.addObject("competition", new Competition());
        modelAndView.setViewName("admin/competition_add");
        return modelAndView;
    }

    @RequestMapping(value = "/competition_add", method = RequestMethod.POST)
    public ModelAndView addCompetition(@ModelAttribute(value = "competition") @Valid Competition competition,
                                       BindingResult bindingResult) {
        long participants = competition.getUsers().length;
        competition.setParticipants(participants);
        Calendar calendar = new GregorianCalendar(2017, 7, 17);
        competition.setDue_date(calendar.getTime());
        competitionService.saveCompetition(competition);

        for (String userEmail : competition.getUsers()) {
            User user = userService.findUserByEmail(userEmail);
            CompetitionUser competitionUser = new CompetitionUser(user, competition);
            competitionUserService.saveUserCompetition(competitionUser);
        }

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

    @RequestMapping(value = "/competition_remove/{id}", method = RequestMethod.GET)
    public ModelAndView addCompetition(@PathVariable("id") int competition_id) {
        ModelAndView modelAndView = new ModelAndView();

        Competition competition = competitionService.findCompetitionById(competition_id);
        List<CompetitionUser> competitionUsers = competitionUserService.findCompetitionUsersByCompetition(competition);

        for (CompetitionUser competitionUser : competitionUsers) {
            competitionUserService.removeCompetitionUserById(competitionUser.getId());
        }
        competitionService.removeCompetition(competition.getId());

        modelAndView.setViewName("admin/competitions");
        return modelAndView;
    }

    private List<Task> findTasksByLevel(String levelName) {
        Level level = levelService.findLevelByName(levelName.toLowerCase());
        return taskService.findAllByLevel(level);
    }
}
