package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.auth.LoginCredentials;
import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.services.LoginService;
import com.coastcapitalsavings.mvc.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/login")
public class LoginController {
    private final LoginService loginService;
    private final JwtService jwtService;

    @Autowired
    public LoginController(LoginService loginService, JwtService jwtService) {
        this.loginService = loginService;
        this.jwtService = jwtService;
    }
    @RequestMapping (path = "", method = POST, produces = APPLICATION_JSON_VALUE)
    public Employee login(@RequestBody LoginCredentials credentials,
                          HttpServletResponse response) {
        return loginService.login(credentials)
                .map(user -> {
                    try {
                        response.setHeader("Token", jwtService.tokenFor(user));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return user;
                })
                .orElseThrow(() -> new FailedToLoginException(credentials.getUsername()));
    }

}
