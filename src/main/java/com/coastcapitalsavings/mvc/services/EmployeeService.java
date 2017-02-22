package com.coastcapitalsavings.mvc.services;

import com.coastcapitalsavings.mvc.models.Employee;

/**
 * Created by Nancy on 2017-02-21.
 */
public class EmployeeService {

    protected Optional<Employee> get(String username) {
        // TODO: call stored procedure to get the employee, can't just use json. 
        return new Employee();
    }
}
