package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data @Entity
public class Employee {
    @Id Integer eid;
    @NotNull String fName;
    @NotNull String lName;
    @NotNull String email;
    @OneToOne Employee reportsTo;

    @ManyToOne @JsonBackReference CostCenter costCenter;
    @OneToMany (mappedBy="submittedBy") @JsonManagedReference Set<Request> requests;
    @ManyToMany (mappedBy="employees") @JsonManagedReference Set<Role> roles;
}
