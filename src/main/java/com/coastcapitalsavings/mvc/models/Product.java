package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * A product that can be requested by an employee
 */
@Data
public class Product {
    private long id;
    private String name;
}
