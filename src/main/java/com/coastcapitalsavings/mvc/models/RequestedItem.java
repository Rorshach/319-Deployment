package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data @Entity
public class RequestedItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @ManyToOne @JsonBackReference Request request;
    @OneToOne ItemStatus itemStatus;
    @ManyToOne @JsonBackReference Product product;
}
