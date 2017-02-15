package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.StringJoiner;

@Data @Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;

    @ManyToMany Collection<Request> requests;
    @ManyToOne Category category;
    @ManyToMany Collection<Profile> profiles;

    public Product(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
