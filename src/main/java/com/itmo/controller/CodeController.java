package com.itmo.controller;

import com.itmo.model.Code;
import com.itmo.service.ByteCodeLoader;
import com.itmo.service.CodeService;
import com.itmo.service.InMemoryJavaCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class CodeController {
    
    @Autowired
    private CodeService codeService;

    @MessageMapping("/send")
    @SendTo("/topic/transfer")
    public Code userCode(Code userCode) throws Exception {
        Thread.sleep(500); // simulated delay
        String className = "UserCode";

        StringBuilder code = new StringBuilder();
        code.append(userCode.getCode());

        int numberOfFirstLine = code.indexOf("\n");
        if (numberOfFirstLine >= 0) {
            code.delete(0, numberOfFirstLine);
        }

        String firstLine = "public class " + className + " {\n";
        code.insert(0, firstLine);
        System.out.println(code.toString());

        System.out.println(codeService.findTask("elementary", 1));

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