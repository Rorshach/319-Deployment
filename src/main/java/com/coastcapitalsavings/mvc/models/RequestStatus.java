package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class RequestStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String status;

    // @OneToMany Collection<Request> requests;

}
