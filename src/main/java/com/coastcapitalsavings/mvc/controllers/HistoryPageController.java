package com.coastcapitalsavings.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the request page
 */
@Controller
public class HistoryPageController {

    @RequestMapping(value = "/history")
    public String mainPage() {
        return "history";
    }
}
