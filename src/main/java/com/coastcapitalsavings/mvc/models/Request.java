package com.coastcapitalsavings.mvc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Request {
    @Id @GeneratedValue(strategy=GenerationType.AUTO) Integer id;
    @NotNull Date dateCreated;
    Date dateModified;
    @ManyToOne @JsonManagedReference Employee lastModifiedBy;
    @ManyToOne @JsonManagedReference Employee submittedBy;
    @ManyToOne @JsonBackReference RequestStatus requestStatus;
    @OneToMany (mappedBy="request") @JsonManagedReference Set<RequestedItem> requestedItems;
    @ManyToMany @JsonManagedReference Set<Product> products;

    public Request(Employee emp, Product prod) {
        this.dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
        this.dateModified = new Date(Calendar.getInstance().getTimeInMillis());
        this.lastModifiedBy = emp;
        this.submittedBy = emp;
        this.products = new HashSet<>();
        products.add(prod);                 // TODO: Hardcoded
    }

    /*
    public Request() {
        //TODO: needs changing
        //HARD CODED!
        CostCenter cc = new CostCenter();
        cc.cid = 11;
        cc.name = "Commerce";

        Employee e = new Employee();
        e.costCenter = cc;
        e.eid = 11;
        e.email = "test@gmail.com";
        e.fName = "test";
        e.lName = "test";

        RequestStatus rs = new RequestStatus();
        rs.id = 21;
        rs.status = "Pending";

        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        this.dateCreated = date;
        this.dateModified = date;
        this.lastModifiedBy = e;
        this.submittedBy = e;
        requestStatus = rs;
    }
    */
}
