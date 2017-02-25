package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * The status of an individual product in a request
 */
@Data
public class ProductStatus {
    private String code;
    private String description;
}
