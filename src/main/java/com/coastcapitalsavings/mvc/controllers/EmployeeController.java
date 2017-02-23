package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.repositories.EmployeeRepository;
//import com.coastcapitalsavings.util.Responses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for creating an employee object for current user after logging in
 */
@Controller
public class EmployeeController {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository es) {
        this.employeeRepository = es;
    }

    /**
     * Fetch the employee object from db using user's email
     * @param userEmail
     * @return Employee if email matches with one entry in db, throws DataAccessException otherwise
     */
    @RequestMapping(method = RequestMethod.POST)
    public Employee getUser(String userEmail) {
        return employeeRepository.getUser(userEmail)
                .orElseThrow(()-> new DataAccessException(userEmail) {
                    @Override
                    public String getMessage(){
                        return super.getMessage();
                    }
                });
    }
}
