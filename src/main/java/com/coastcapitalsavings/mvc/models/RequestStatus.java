package com.coastcapitalsavings.mvc.models;

import lombok.Data;

/**
 * The status of a request
 */
@Data
public class RequestStatus {
    private String code;
    private String description;
}
