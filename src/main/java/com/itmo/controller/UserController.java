package com.itmo.controller;

import com.itmo.model.Competition;
import com.itmo.model.Role;
import com.itmo.model.Task;
import com.itmo.model.User;
import com.itmo.model.components.CompetitionUser;
import com.itmo.service.CodeService;
import com.itmo.service.CompetitionService;
import com.itmo.service.CompetitionUserService;
import com.itmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController extends ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CompetitionUserService competitionUserService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ADMIN")) {
                return new ModelAndView("redirect:admin");
            }
        }
        boolean competition = true;
        boolean starts = false;
        modelAndView.addObject("competition", competition);
        modelAndView.addObject("starts", starts);
        modelAndView.setViewName("user/index");
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
        return modelAndView;
    }

    @RequestMapping(value = {"/single/{level}/{test_id}"}, method = RequestMethod.GET)
    public ModelAndView singlePage(@PathVariable("level") String level, @PathVariable("test_id") int testID) {
        ModelAndView modelAndView = new ModelAndView();

        String levelXML = setLevelTask(level);

        Map testValues = codeService.findTask(levelXML, testID);
        Task task = new Task(testValues.get("name").toString(), testValues.get("description").toString(),
                testValues.get("input").toString(), testValues.get("output").toString());

        modelAndView.addObject("task", task);
        modelAndView.setViewName("user/single");
        return modelAndView;
    }

    @RequestMapping(value = "/multi/{level}/{test_id}", method = RequestMethod.GET)
    public ModelAndView multiPage(@PathVariable("level") String level, @PathVariable("test_id") int testID) {
        ModelAndView modelAndView = new ModelAndView();

        String levelXML = setLevelTask(level);

        Map testValues = codeService.findTask(levelXML, testID);
        Task task = new Task(testValues.get("name").toString(), testValues.get("description").toString(),
                testValues.get("input").toString(), testValues.get("output").toString());

        modelAndView.setViewName("user/multiprogramming");
        modelAndView.addObject("task", task);
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.addObject("user", user);
        if (user.getRoles().iterator().next().getRole().equals("USER")) {
            modelAndView.setViewName("user/profile");
        } else {
            modelAndView.setViewName("admin/profile");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView editUser(User user) {
        ModelAndView modelAndView = new ModelAndView();
        userService.modifyUser(user);
        User userDB = findUser();
        modelAndView.addObject("user", userDB);
        if (userDB.getRoles().iterator().next().getRole().equals("USER")) {
            modelAndView.setViewName("user/profile");
        } else {
            modelAndView.setViewName("admin/profile");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/competitions", method = RequestMethod.GET)
    public ModelAndView userCompetitions() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        List<CompetitionUser> competitionUsers = competitionUserService.findCompetitionUsersByUser(user);
        Map<Competition, CompetitionUser> competitionUserMap = new HashMap<>();
        for (CompetitionUser competitionUser : competitionUsers) {
            competitionUserMap.put(competitionUser.getCompetition(), competitionUser);
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("competitions", competitionUserMap);
        modelAndView.setViewName("user/competitions");
        return modelAndView;
    }

    @RequestMapping(value = "/competition/{id}", method = RequestMethod.GET)
    public ModelAndView addCompetition(@PathVariable("id") int competition_id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        Competition competition = competitionService.findCompetitionById(competition_id);
        Set<Task> tasks = competition.getTasks();
        modelAndView.addObject("tasks", tasks);
        modelAndView.setViewName("user/competition");
        return modelAndView;
    }

    private String setLevelTask(String level) {
        switch (level) {
            case "elem":
                return ("ELEMENTARY");
            case "aver":
                return ("AVERAGE");
            case "heavy":
                return ("HEAVY");
            default:
                return ("ELEMENTARY");
        }
    }
}