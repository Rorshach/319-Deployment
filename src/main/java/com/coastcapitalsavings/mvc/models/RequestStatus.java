package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * The status of a request
 */
@Data
public class RequestStatus {
    private long id;
    private String status;
}
