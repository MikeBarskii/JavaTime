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
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        User user = findUser();
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("user/main");
        return modelAndView;
    }

    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public ModelAndView singlePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/singleElementary");
        return modelAndView;
    }

    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    public ModelAndView multiPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/multiprogramming");
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
}
