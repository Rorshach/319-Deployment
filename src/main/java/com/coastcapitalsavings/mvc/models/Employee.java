package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter
@Setter
@Entity
public class Employee {
    @Id Integer eid;
    @NotNull String fName;
    @NotNull String lName;
    @NotNull String email;
    @OneToOne Employee reportsTo;

    @ManyToOne @JsonBackReference CostCenter costCenter;
    @OneToMany (mappedBy="submittedBy") @JsonBackReference Set<Request> requests;
    @ManyToMany (mappedBy="employees") @JsonManagedReference Set<Role> roles;
}
