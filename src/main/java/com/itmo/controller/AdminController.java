package com.itmo.controller;

import com.itmo.model.User;
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
    private UserService userService;

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
//        List<User> users = userService.findUsersByActive(1);
//        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/competition_add");
        return modelAndView;
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
