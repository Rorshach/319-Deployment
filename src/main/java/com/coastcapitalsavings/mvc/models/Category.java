package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer cid;

    String name;

    @OneToMany (mappedBy="category") @JsonManagedReference Set<Product> products;
}
