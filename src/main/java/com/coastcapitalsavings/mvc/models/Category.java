package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;

import java.util.List;

/**
 * A category of products.
 */
@Data
public class Category {
    @JsonView(ModelViews.Summary.class) private Integer cid;
    @JsonView(ModelViews.Summary.class) private String name;
    private List<Product> products;
}
