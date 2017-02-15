package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String dateCreated;
    String dateModified;
    String modifiedBy;

    @ManyToOne Employee submitter;
    @ManyToOne RequestStatus requestStatus;
    @OneToMany Collection<RequestedItem> requestedItems;
    @ManyToMany Collection<Product> products;

    public Request(int id, String dateCreated, String dateModified, String modifiedBy) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.modifiedBy = modifiedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


}
