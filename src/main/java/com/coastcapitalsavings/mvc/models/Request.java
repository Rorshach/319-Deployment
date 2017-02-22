package com.coastcapitalsavings.mvc.models;

import com.coastcapitalsavings.util.serializers.JsonTimeStampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a request for a set 1-3 products that is submitted by an employee.
 */
@Data
public class Request {
    private long id;            // seems like MySQL unsigned int == java long
    private String notes;
    private Timestamp dateCreated;
    private int submittedBy_employeeId;
    private Timestamp dateModified;
    private int lastModifiedBy_employeeId;
    private long requestStatus_id;
    private List<RequestProduct> products;       // Storing as a list stops us from having to write a painful map serializer


    // These two methods are supplied so that we can use a custom serializer
    // to return a string instead of a millisecond count

    @JsonSerialize(using=JsonTimeStampSerializer.class)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    @JsonSerialize(using=JsonTimeStampSerializer.class)
    public Timestamp getDateModified() {
        return dateModified;
    }
};


