package com.coastcapitalsavings.mvc.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ItemStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;

    @OneToOne RequestedItem requestedItem;
}
