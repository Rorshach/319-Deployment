package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;


@Data @Entity
public class Employee {
    @Id int eid;
    String fName;
    String lName;
    String email;
    String reportTo;

    @ManyToOne CostCenter costCenter;
    @OneToMany Collection<Request> collection;
}
