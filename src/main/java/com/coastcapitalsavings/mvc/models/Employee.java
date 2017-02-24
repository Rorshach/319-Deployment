package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import java.util.List;

/**
 * An employee
 */
@Data
public class Employee {
    private Long id;
    private String fName;
    private String lName;
    private String email;
    private Employee reportsTo;
    private CostCenter costCenter;
    private List<Request> requests;
    private Role role;
    private String password;

}
