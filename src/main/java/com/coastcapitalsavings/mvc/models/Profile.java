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
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;

    @ManyToMany (cascade = CascadeType.ALL) @JsonManagedReference Set<Product> products;
}
