package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;

    @ManyToMany Collection<Request> requests;
    @ManyToOne Category category;
    @ManyToMany Collection<Profile> profiles;
}
