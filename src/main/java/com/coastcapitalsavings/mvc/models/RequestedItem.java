package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class RequestedItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne Collection<Request> requests;
    @OneToOne ItemStatus itemStatus;
}
