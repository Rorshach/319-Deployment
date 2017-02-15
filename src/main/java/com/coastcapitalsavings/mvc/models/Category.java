package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;;
    String desc;

    @OneToMany Collection<Product> products;
}
