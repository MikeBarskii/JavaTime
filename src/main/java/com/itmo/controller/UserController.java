package com.itmo.controller;

import com.itmo.model.Task;
import com.itmo.model.User;
import com.itmo.service.CodeService;
import com.itmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CodeService codeService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
        modelAndView.setViewName("user/index");
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

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView editUser(User user) {
        ModelAndView modelAndView = new ModelAndView();
        userService.modifyUser(user);
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    private User findUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByEmail(auth.getName());
    }

    private String setLevelTask(String level){
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
