package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * A product that can be requested by an employee
 */
@Data
public class Product {
    private int id;
    private String name;
}
