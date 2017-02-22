package com.coastcapitalsavings.mvc.services;



import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.auth.LoginCredentials;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


/**
 * Data access object for login endpoint
 */

public class LoginService {

    private EmployeeService employeeService;

    @Autowired
    public LoginService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // TODO: figure out hashing mechanism
    public Optional<Employee> login(LoginCredentials credentials) {
        return employeeService.get(credentials.getUsername())
                .filter(profile -> profile.getLogin().getPassword().equals(credentials.getPassword()))
                .map(profile -> new MinimalProfile(profile));
    }

}



