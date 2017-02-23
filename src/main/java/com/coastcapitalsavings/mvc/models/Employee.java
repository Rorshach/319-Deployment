package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import java.util.List;

/**
 * An employee
 */
@Data
public class Employee {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private Employee reportsTo;
    private CostCenter costCenter;
    private List<Request> requests;
    private List<Role> roles;
    private String password;
}
