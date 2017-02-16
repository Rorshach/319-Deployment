package com.coastcapitalsavings.mvc.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data @Entity
public class RequestStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String status;
    @OneToMany @JsonManagedReference Set<Request> requests;

}
