package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;

    @ManyToMany Collection<Product> products;

    public Profile(int id, String desc) {
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
