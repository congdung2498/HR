package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {


    @GetMapping("/")
    public String login() {
        return "login.html";
    }

//    @GetMapping("/rest/index")
//    public String index() {
//        return "index.html";
//    }
//    @GetMapping("/index")
//    public String welcome() {
//        return "index.html";
//    }
//
//    @GetMapping("/admin")
//    public String admin() {
//        return "admin";
//    }
//
//    @GetMapping("/403")
//    public String accessDenied() {
//        return "403";
//    }
//
//    @GetMapping("/login")
//    public String getLogin() {
//        return "login.html";
//    }

}