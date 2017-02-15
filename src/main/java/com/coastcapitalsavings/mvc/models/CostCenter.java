package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data @Entity
public class CostCenter {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;

    @OneToMany Collection<Employee> employees;
}
