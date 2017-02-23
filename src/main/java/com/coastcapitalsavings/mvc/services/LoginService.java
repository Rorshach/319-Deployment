package com.coastcapitalsavings.mvc.services;



import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.auth.LoginCredentials;


import com.coastcapitalsavings.mvc.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


/**
 * Data access object for login endpoint
 */
@Component
public class LoginService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public LoginService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // TODO: figure out hashing mechanism

    /**
     * Matches user email and password and return an Employee object from EmployeeRepository if succeeds
     * @param credentials this is a bundled-up userEmail + password
     * @return Employee if credentials match that in the db, otherwise empty
     */
    public Optional<Employee> login(LoginCredentials credentials) {
        return employeeRepository.getUser(credentials.getUserEmail())
                .filter(user -> user.getPassword().equals(credentials.getPassword()));
    }

}



