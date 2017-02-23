package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.auth.LoginCredentials;
import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.services.LoginService;
import com.coastcapitalsavings.mvc.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/authenticate")
public class LoginController {
    private final LoginService loginService;
    private final JwtService jwtService;

    @Autowired
    public LoginController(LoginService loginService, JwtService jwtService) {
        this.loginService = loginService;
        this.jwtService = jwtService;
    }

    /**
     * Log into the system using given credentials and the
     * @param credentials
     * @param response
     * @modifies response
     * @return Employee if the credentials pass, otherwise throw exception
     */
    @RequestMapping (path = "", method = POST, produces = APPLICATION_JSON_VALUE)
    public Employee login(@RequestBody LoginCredentials credentials,
                          HttpServletResponse response) {
        return loginService.login(credentials)
                .map(user -> {
                    try {
                        // tokenize the user's response
                        String token = jwtService.tokenFor(user);
                        // set token to response header
                        response.setHeader("Token", token);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return user;
                })
                // TODO: use a better exception
                .orElseThrow(() -> new DataAccessException(credentials.getUserEmail()) {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                });
    }

}
