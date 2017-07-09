package com.itmo.controller;

import com.itmo.model.User;
import com.itmo.service.UserService;
import edu.vt.middleware.password.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult
                    .rejectValue("confirmPassword", "error.user",
                            "Пароли не совпадают");
        }

        String checkPassword = checkUserPassword(user.getPassword());
        if (!checkPassword.equals("true")) {
            bindingResult
                    .rejectValue("confirmPassword", "error.user",
                            checkPassword);
        }

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
        } else {
            userService.saveUser(user);
            return new ModelAndView("user/index");
        }
        return modelAndView;
    }

    private String checkUserPassword(String password) {
        QwertySequenceRule qwertySeqRule = new QwertySequenceRule();
        LengthRule lengthRule = new LengthRule(8, 16);
        AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule();

        CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
        charRule.getRules().add(new DigitCharacterRule(1));
        charRule.getRules().add(new NonAlphanumericCharacterRule(1));
        charRule.getRules().add(new UppercaseCharacterRule(1));
        charRule.getRules().add(new LowercaseCharacterRule(1));

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(qwertySeqRule);
        ruleList.add(lengthRule);
        ruleList.add(charRule);
        ruleList.add(alphaSeqRule);

        PasswordValidator validator = new PasswordValidator(ruleList);
        PasswordData passwordData = new PasswordData(new Password(password));

        RuleResult result = validator.validate(passwordData);
        if (result.isValid()) {
            return "true";
        } else {
            return validator.getMessages(result).iterator().next();
        }
    }
}
