package com.itmo.controller;

import com.itmo.model.Code;
import com.itmo.service.ByteCodeLoader;
import com.itmo.service.InMemoryJavaCompiler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class CodeController {

    @MessageMapping("/send")
    @SendTo("/topic/transfer")
    public Code userCode(Code userCode) throws Exception {
        Thread.sleep(500); // simulated delay
        String className = "UserCode";

        StringBuilder code = new StringBuilder();
        code.append("public class ").append(className).append(" {");
        code.append("public int result() {");
        code.append("int output = 256;");
        code.append(userCode.getCode());
        code.append("}}");

        System.out.println(code.toString());

        byte[] byteCode = InMemoryJavaCompiler.compile(className, code.toString());
        Class fooClass = ByteCodeLoader.load(className, byteCode);

        Object obj = fooClass.newInstance();
        System.out.println(obj.equals(new Object()));

        return new Code(userCode.getCode() + "!!!");
    }

    @MessageMapping("/result")
    @SendTo("/topic/checkcode")
    public List<Boolean> checkCode(Code userCode) {
        List<Boolean> resultList = Arrays.asList(true, false, false, false, false);
        return resultList;
    }
}