package com.coastcapitalsavings.mvc.models;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    @ManyToMany (cascade = CascadeType.ALL) Collection<Product> products;
}
