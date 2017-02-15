package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data @Entity
public class RequestedItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @ManyToOne Request request;
    @OneToOne ItemStatus itemStatus;
    //@ManyToOne Product product;
}
