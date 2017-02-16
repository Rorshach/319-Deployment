package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data @Entity
public class Employee {
    @Id int eid;

    @NotNull String fName;
    @NotNull String lName;
    @NotNull String email;
    @OneToOne Employee reportsTo;

    @ManyToOne CostCenter costCenter;
    @OneToMany (mappedBy="submittedBy")Set<Request> requests;
    @ManyToMany (mappedBy="employees") Set<Role> roles;
}
