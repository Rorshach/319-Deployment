package com.coastcapitalsavings.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the request page
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/admin")
    public String mainPage() {
        return "admin";
    }
}