package com.coastcapitalsavings.mvc.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    @ManyToMany @JsonBackReference Set<Employee> employees;
}