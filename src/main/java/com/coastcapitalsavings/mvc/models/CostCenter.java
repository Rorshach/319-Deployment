package com.coastcapitalsavings.mvc.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class CostCenter {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer cid;

    String name;

    @OneToMany(mappedBy="costCenter") @JsonManagedReference Set<Employee> employees;

}
