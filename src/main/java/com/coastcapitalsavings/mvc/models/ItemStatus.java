package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;

@Data @Entity
public class ItemStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String desc;

    @OneToOne RequestedItem requestedItem;

    public ItemStatus(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
