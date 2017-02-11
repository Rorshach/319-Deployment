package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;


@Data @Entity
public class Employee {
    @Id int eid;
    String fName;
    String lName;

    @ManyToOne CostCenter costCenter;
    @OneToMany <Collection>Request;
}


