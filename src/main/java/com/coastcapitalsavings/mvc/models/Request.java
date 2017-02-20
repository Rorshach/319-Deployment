package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import java.sql.Date;
import java.util.Map;

/**
 * Represents a request for a set 1-3 products that is submitted by an employee.
 */
@Data
public class Request {
    private int id;
    private String notes;
    private Date dateCreated;
    private Employee submittedBy;
    private Date dateModified;
    private Employee lastModifiedBy;
    private RequestStatus requestStatus;
    private Map<Product, ProductStatus> products;
}
