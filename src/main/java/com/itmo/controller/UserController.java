package com.itmo.controller;

import com.itmo.model.User;
import com.itmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView userPage() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("user/main");
        return modelAndView;
    }

    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public ModelAndView singlePage() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.setViewName("user/singleElementary");
        return modelAndView;
    }

    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    public ModelAndView multiPage1() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.setViewName("user/multiprogramming");
        return modelAndView;
    }

    private User findUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByEmail(auth.getName());
    }
}
