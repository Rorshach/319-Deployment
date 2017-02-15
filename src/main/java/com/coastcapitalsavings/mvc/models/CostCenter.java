package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class CostCenter {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int cid;

    String name;

    @OneToMany(mappedBy="costCenter") Set<Employee> employees;

}
