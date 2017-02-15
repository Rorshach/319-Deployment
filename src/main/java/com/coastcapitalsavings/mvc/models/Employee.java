package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Data @Entity
public class Employee {
    @Id int eid;
    String fName;
    String lName;
    String email;
    @OneToOne Employee reportsTo;

    //@ManyToOne CostCenter costCenter;
    @OneToMany (mappedBy="submittedBy")Set<Request> requests;
    @ManyToMany (mappedBy="employees") Set<Role> roles;
}
