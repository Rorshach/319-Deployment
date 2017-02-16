package com.coastcapitalsavings.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the request page
 */
@Controller
public class MainPageController {

    @RequestMapping(value = "/")
    public String mainPage() {
            return "index";
        }
}
