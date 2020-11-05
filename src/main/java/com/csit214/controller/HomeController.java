package com.csit214.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>FlyDreamAir</title>\n" +
                "\t<style type=\"text/css\">\n" +
                "\t\t\n" +
                "\t\tbody{  \n" +
                "  font-family:Helvetica;   \n" +
                "  font-size:1em;\n" +
                "  width:100%;\n" +
                "  margin:0px;\n" +
                "  padding:0px;\n" +
                "}\n" +
                ".button{  \n" +
                "  display:inline-block;\n" +
                "  color:white;\n" +
                "  background-color:#00b2b0;\n" +
                "  padding:5px;\n" +
                "  border-radius:5px;  \n" +
                "  text-shadow:1px 1px 1px grey;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "/*header*/\n" +
                "header{ \n" +
                "  padding:30px;  \n" +
                "  background-image: url(\"https://iqbookings.com/images/ticket.jpg\"); no-repeat; max-width:100%;\n" +
                "  background-repeat:no-repeat;  \n" +
                "  filter:grayscale( 0% );  \n" +
                "  background-position: center center;  \n" +
                "  background:cover; \n" +
                "  height: 100vh;\n" +
                "}\n" +
                "#left{\n" +
                "  display:inline-block;\n" +
                "  width:50%;   \n" +
                "  margin-left:50px;\n" +
                "  vertical-align:middle;    \n" +
                "  color:white;  \n" +
                "} \n" +
                "#left h2{\n" +
                "  font-size:2.5em;\n" +
                "  text-shadow:1px 1px 2px grey;\n" +
                "}\n" +
                "header img{  \n" +
                "  vertical-align:middle;\n" +
                "  display:inline-block;   \n" +
                "  margin-left:10%;\n" +
                "  width:25%;\n" +
                "  height: 100vh;\n" +
                "  background: cover;\n" +
                "}\n" +
                "\n" +
                "h3 a{\n" +
                "\tcolor: white;\n" +
                "\ttext-decoration: none;\n" +
                "}\n" +
                "\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "\n" +
                "<header>\n" +
                "  <div id=\"left\">  \n" +
                "  <h2>Land your dream.</h2>\n" +
                "  <h4>Join our generous frequent flyer program.</h4>\n" +
                "  <h3 class=\"button\"> <a href=\"https://flydreamair-react.herokuapp.com/\"> Join FlyDreamAir</a></h3>\n" +
                "\n" +
                "</header>\n" +
                "\n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "</body>\n" +
                "</html>";
    }

}