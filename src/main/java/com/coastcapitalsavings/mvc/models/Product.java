package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;
    String name;

    @ManyToMany (cascade=CascadeType.ALL) @JsonBackReference Set<Request> requests;
    @ManyToOne @JsonBackReference Category category;
    @ManyToMany (mappedBy="products") @JsonManagedReference Set<Profile> profiles;

}
