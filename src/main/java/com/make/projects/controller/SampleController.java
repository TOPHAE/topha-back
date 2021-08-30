package com.make.projects.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class SampleController {

    @GetMapping("/")
    public @ResponseBody
    String index(){

        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }
}
