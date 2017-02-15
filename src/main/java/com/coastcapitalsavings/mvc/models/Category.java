package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int cid;

    String name;

    @OneToMany (mappedBy="category") Set<Product> products;
}
