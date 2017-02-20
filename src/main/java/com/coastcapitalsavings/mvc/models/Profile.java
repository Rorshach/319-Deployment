package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import java.util.List;

/**
 * A profile is a set of products which are linked to an employee job description.
 */
@Data
public class Profile {
    private int id;
    private String name;
    private List<Product> products;
}
