package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Data @Entity
public class Request {
    @Id @GeneratedValue(strategy=GenerationType.AUTO) int id;
    @NotNull Date dateCreated;
    Date dateModified;
    @ManyToOne Employee lastModifiedBy;
    @ManyToOne Employee submittedBy;
    @ManyToOne RequestStatus requestStatus;
    @OneToMany (mappedBy="request") Set<RequestedItem> requestedItems;
    @ManyToMany Set<Product> products;
}
