package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;

@Data @Entity
public class ItemStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;

    @OneToOne RequestedItem requestedItem;
}
