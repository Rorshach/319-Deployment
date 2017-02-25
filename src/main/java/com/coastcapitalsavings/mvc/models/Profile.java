package com.coastcapitalsavings.mvc.models;

import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

/**
 * A profile is a set of products which are linked to an employee job description.
 */
@Data
public class Profile {
    @JsonView(ModelViews.Summary.class) private String code;
    @JsonView(ModelViews.Summary.class) private String description;
    private List<Product> products;
}
