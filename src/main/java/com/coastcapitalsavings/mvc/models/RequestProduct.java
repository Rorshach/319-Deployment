package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * A product in a request
 */
@Data
public class RequestProduct {
    Product product;
    long productStatus_id;
}
