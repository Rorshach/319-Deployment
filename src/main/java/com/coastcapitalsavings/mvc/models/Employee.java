package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;


@Data @Entity
public class Employee {
    @Id int eid;
    String fName;
    String lName;
    String email;
    String reportToId;

    @ManyToOne CostCenter costCenter;
    @OneToMany Collection<Request> collection;

    //Note: Unsure if Employee should also be made of a CostCenter, not entirely sure how to implement.
    public Employee(int id, String fname, String lName, String email, String reportToId) {
        this.eid = id;
        this.fName = fname;
        this.lName = lName;
        this.email = email;
        this.reportToId = reportToId;
    }

    public int getId() {
        return eid;
    }

    public void setId(int id) {
        this.eid = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReportToId() {
        return reportToId;
    }

    public void setReportToId(String id) {
        this.reportToId = id;
    }
}
