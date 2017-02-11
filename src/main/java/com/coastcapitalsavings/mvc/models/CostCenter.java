package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data @Entity
public class CostCenter {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;
}
