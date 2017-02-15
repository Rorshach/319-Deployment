package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data @Entity
public class RequestedItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    //@ManyToOne Set<Request> requests;
    @OneToOne ItemStatus itemStatus;
    //@ManyToOne Product product;
}
