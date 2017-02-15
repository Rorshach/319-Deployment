package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    String name;

    @ManyToMany (cascade=CascadeType.ALL)
    Set<Request> requests;

    //@ManyToOne Category category;

    @ManyToMany (mappedBy="products")
    Set<Profile> profiles;

}
