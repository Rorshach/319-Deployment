package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;

    @ManyToMany (cascade = CascadeType.ALL) @JsonManagedReference Set<Product> products;
}
