package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.services.EmployeeService;
import com.coastcapitalsavings.mvc.services.LoginService;
//import com.coastcapitalsavings.util.Responses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.soap.AddressingFeature;

/**
 * Controller for creating a user (employee)
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService es) {
        this.employeeService = es;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Employee getUser(String userName) {
        return employeeService.getUser(userName)
                .orElseThrow(()-> new ProfileNotFoundException(userName));
    }
}
