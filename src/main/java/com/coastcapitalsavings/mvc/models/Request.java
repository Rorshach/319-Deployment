package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Request {
    @JsonIgnoreProperties
    @Id @GeneratedValue(strategy=GenerationType.AUTO) Integer id;
    @NotNull Date dateCreated;
    Date dateModified;
    @ManyToOne @JsonManagedReference Employee lastModifiedBy;
    @ManyToOne @JsonManagedReference Employee submittedBy;
    @ManyToOne @JsonBackReference RequestStatus requestStatus;
    @OneToMany (mappedBy="request") @JsonManagedReference Set<RequestedItem> requestedItems;
    @ManyToMany @JsonManagedReference Set<Product> products;
}
