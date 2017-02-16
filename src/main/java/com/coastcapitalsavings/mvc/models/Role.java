package com.coastcapitalsavings.mvc.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    @ManyToMany @JsonBackReference Set<Employee> employees;
}