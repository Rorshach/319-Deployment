package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import java.util.List;

/**
 * A category of products.
 */
@Data
public class Category {
    private Integer cid;
    private String name;
    private List<Product> products;
}
