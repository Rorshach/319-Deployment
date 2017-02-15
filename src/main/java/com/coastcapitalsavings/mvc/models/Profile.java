package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;

    @ManyToMany Collection<Product> products;
}
