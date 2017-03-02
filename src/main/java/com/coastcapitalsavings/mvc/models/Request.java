package com.coastcapitalsavings.mvc.models;

import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;
import com.coastcapitalsavings.util.serializers.JsonTimeStampSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a request for a set 1-3 products that is submitted by an employee.
 */
@Data
public class Request {
    @JsonView(ModelViews.Summary.class) private long id;        // mysql BIGINT
    private String notes;
    @JsonView(ModelViews.Summary.class) private Timestamp dateCreated;
    @JsonView(ModelViews.Summary.class) private String submittedBy;
    @JsonView(ModelViews.Summary.class) private Timestamp dateModified;
    @JsonView(ModelViews.Summary.class)private String lastModifiedBy;
    @JsonView(ModelViews.Summary.class) private String statusCode;
    private List<RequestProduct> products;       // Storing as a list stops us from having to write a painful map serializer


    // These two methods are supplied so that we can use a custom serializer
    // to return a string instead of a millisecond count

    @JsonSerialize(using=JsonTimeStampSerializer.class)
    @JsonView(ModelViews.Summary.class) public Timestamp getDateCreated() {
        return dateCreated;
    }

    @JsonSerialize(using=JsonTimeStampSerializer.class)
    public Timestamp getDateModified() {
        return dateModified;
    }
};


