package com.coastcapitalsavings.mvc.services;

import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepo;

    public Request postNewRequest(String notes, int[] productsIds) {
        Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
        int currentEmployee = 1;             // TODO: Hardcoded until spring security spike
        Request req = new Request();
        req.setNotes(notes);
        req.setDateCreated(ts);
        req.setDateModified(ts);
        req.setSubmittedBy_employeeId(currentEmployee);
        req.setLastModifiedBy_employeeId(currentEmployee);
        req.setRequestStatus_id(1);         // TODO:  Hardcoded until status enum sorted out

        Request postedRequest = requestRepo.postNewRequest(req);

        for (int id : productsIds) {
            requestRepo.addProductToRequest(id, postedRequest);
        }

        return postedRequest;
    }
}
