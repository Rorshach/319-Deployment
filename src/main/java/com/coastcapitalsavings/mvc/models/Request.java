package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String dateCreated;
    String dateModified;
    Employee modifiedBy;

    @ManyToOne Employee submitter;
    @ManyToOne RequestStatus requestStatus;
    @OneToMany Collection<RequestedItem> requestedItems;
    @ManyToMany Collection<Product> products;
}
