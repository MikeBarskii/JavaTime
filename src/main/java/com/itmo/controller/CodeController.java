package com.itmo.controller;

import com.itmo.model.Code;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CodeController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Code userCode(Code userCode) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Code(userCode.getCode() + "!!!");
    }

}
