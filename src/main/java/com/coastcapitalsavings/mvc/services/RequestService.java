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
     * @param productsIds list of product ids
     * @return
     */
    public Request postNewRequest(String notes, long[] productsIds) {
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
        List<RequestProduct> reqProducts = new ArrayList<>();

        for (long id : productsIds) {
            reqProducts.add(productRepo.addProductToRequest(id, postedRequest.getId()));
        }
        postedRequest.setProducts(reqProducts);
        return postedRequest;
    }

    /**
     * Updates the status id of a row in the request table, then returns that request object, including
     * it's products
     * @param reqId
     * @param newStatusid
     * @return
     */
    public Request putNewRequestStatus(long reqId, int newStatusid) {
        if (requestRepo.checkRequestExists(reqId)) {
            Request updatedRequest = requestRepo.putRequestNewStatusId(reqId, newStatusid);
            List<RequestProduct> reqProds = productRepo.getRequestProductsinRequestByRequestId(updatedRequest.getId());
            updatedRequest.setProducts(reqProds);
            return updatedRequest;
        } else {
            throw new DataRetrievalFailureException("Cannot find resource in database: Request: id " +reqId);
        }
    }
}
