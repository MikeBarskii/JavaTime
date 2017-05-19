package com.itmo.controller;

import com.itmo.model.UserCode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class UserCodeController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public UserCode userCode(UserCode userCode) throws Exception {
        Thread.sleep(500); // simulated delay
        return new UserCode(userCode.getCode() + "!!!");
    }

}
