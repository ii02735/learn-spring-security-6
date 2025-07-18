package com.ii02735.learn_spring_security_6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World user";
    }

    @GetMapping("/write")
    public String write() {
        return "Page that'll have the capacity to write things";
    }
}
