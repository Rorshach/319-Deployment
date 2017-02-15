package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class RequestStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String status;
    @OneToMany Set<Request> requests;

}
