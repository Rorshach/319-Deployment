package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class RequestStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String status;

    @OneToMany Collection<Request> requests;

    public RequestStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
