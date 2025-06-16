package com.ii02735.learn_spring_security_6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("")
    public String index() {
        return "This is the index page : everyone has access to it";
    }

}
