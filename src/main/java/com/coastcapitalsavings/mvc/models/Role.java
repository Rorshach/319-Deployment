package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    @ManyToMany
    Collection<Employee> employees;
}