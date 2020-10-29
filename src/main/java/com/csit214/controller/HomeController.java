package com.csit214.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "<div>" +
                "<h2>FlyDreamAir Server is successfully initiated</h2>" +
                "<h3>Access the app <a href='https://flydreamair-react.herokuapp.com/'> here</a></h3>"+
                "</div>";
    }

    @RequestMapping("/error")
    public String error(){
        return "<div>" +
                "<h2>Request rejected</h2>" +
                "</div>";
    }
}