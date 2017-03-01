package com.coastcapitalsavings.mvc.services;


import com.coastcapitalsavings.mvc.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataRetrievalFailureException;

import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.models.RequestProduct;
import com.coastcapitalsavings.mvc.repositories.ProductRepository;
import com.coastcapitalsavings.mvc.repositories.RequestRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepo;

    @Autowired
    ProductRepository productRepo;

    public Request getRequestById(long reqId) {
        if (requestRepo.checkRequestExists(reqId)) {
            Request r = requestRepo.getRequestById(reqId);
            List<RequestProduct> products = productRepo.getRequestProductsinRequestByRequestId(reqId);
            r.setProducts(products);
            return r;
        } else {
            throw new DataRetrievalFailureException("Cannot find resource in database: Request: id " + reqId);
        }
    }

    /**
     * Posts a new request by first adding it to the database, then adding each product individually to
     * the join table.  Returns the request object afterwards.
     * @param notes notes for the request
     * @param productsCodes list of product codes
     * @return
     */
    public Request postNewRequest(String notes, String[] productsCodes) {
        Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
        String currentEmployee = "DUMMY";                // TODO: Hardcoded until spring security spike
        String pendingStatusCode = "PEND";               // TODO:  Hardcoded until status enum sorted out
        Request req = new Request();
        req.setNotes(notes);
        req.setDateCreated(ts);
        req.setDateModified(ts);
        req.setSubmittedBy(currentEmployee);
        req.setLastModifiedBy(currentEmployee);
        req.setStatusCode(pendingStatusCode);

        Request postedRequest = requestRepo.postNewRequest(req);
        List<RequestProduct> reqProducts = new ArrayList<>();

        for (String code : productsCodes) {
            reqProducts.add(productRepo.addProductToRequest(code, postedRequest.getId()));
        }
        postedRequest.setProducts(reqProducts);
        return postedRequest;
    }

    /**
     * Updates the status id of a row in the request table, then returns that request object, including
     * it's products
     * @param reqId Id of the request recoed to be updated
     * @param newStatusCode Staus code to be posted
     * @return
     */
    public Request putNewRequestStatus(long reqId, String newStatusCode) {
        if (requestRepo.checkRequestExists(reqId)) {
            Request updatedRequest = requestRepo.putRequestNewStatusId(reqId, newStatusCode);
            List<RequestProduct> reqProds = productRepo.getRequestProductsinRequestByRequestId(updatedRequest.getId());
            updatedRequest.setProducts(reqProds);
            return updatedRequest;
        } else {
            throw new DataRetrievalFailureException("Cannot find resource in database: Request: id " +reqId);
        }
    }
}
