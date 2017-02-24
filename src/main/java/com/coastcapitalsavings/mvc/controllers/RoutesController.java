package com.coastcapitalsavings.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller serving routes.
 */
@Controller
public class RoutesController {

    @RequestMapping(value = "/")
    public String requestPage() {
        return "index";
    }
    @RequestMapping(value = "/login")
    public String loginPage(){
        return "login";
    }
    @RequestMapping(value = "/history")
    public String historyPage() {
        return "history";
    }
    @RequestMapping(value = "/admin")
    public String adminPage() {
        return "admin";
    }
}
