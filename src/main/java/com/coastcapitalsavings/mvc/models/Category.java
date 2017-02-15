package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int cid;
    String desc;

    @OneToMany Collection<Product> products;

    public Category(int id, String desc) {
        this.cid = id;
        this.desc = desc;
    }

    public int getId() {
        return cid;
    }

    public void setId(int id) {
        this.cid = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
