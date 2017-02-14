package com.coastcapitalsavings.mvc.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data @Entity
public class RequestStatus {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String status;
}
