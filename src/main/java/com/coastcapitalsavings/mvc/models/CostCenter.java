package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * A cost center is a billable department (or equivalent) that an employee can belong to.
 */
@Data
public class CostCenter {
    private int id;
    private String name;
}
