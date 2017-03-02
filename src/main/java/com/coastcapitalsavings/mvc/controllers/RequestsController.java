package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;
import com.coastcapitalsavings.mvc.repositories.RequestRepository;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.services.RequestService;
import com.coastcapitalsavings.util.Responses;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

/**
 * Handles all requests to the requests resource
 */
@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    RequestService requestService;

    //TODO:  This endpoint will need to be locked down once authentication is resolved
    @JsonView(ModelViews.Summary.class)
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Request>> getRequestsByDateRange(@RequestParam(required = false) String from,
                                                                @RequestParam(required = false) String to) {
        try {
            List<Request> requests = requestService.getRequestsByDateRange(from, to);
            return new ResponseEntity(requests, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity(Responses.INVALID_PARAMETER_VALUE, HttpStatus.BAD_REQUEST);
        } catch (DateTimeException e) {
            return new ResponseEntity(Responses.INVALID_PARAMETER_VALUE, HttpStatus.PRECONDITION_FAILED);
        } catch (DataAccessException e) {
            return new ResponseEntity(Responses.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Request> postNewRequest(@RequestBody PostBodyInput input) {
        if (input.products == null) {
            return new ResponseEntity(Responses.MISSING_REQUIRED_PARAMETER, HttpStatus.BAD_REQUEST);
        } else if (input.products.length < 1 || input.products.length > 3) {
            return new ResponseEntity(Responses.INVALID_PARAMETER_LENGTH, HttpStatus.PRECONDITION_FAILED);
        } else if (input.notes.length() > 255) {
            return new ResponseEntity(Responses.INVALID_PARAMETER_LENGTH, HttpStatus.PRECONDITION_FAILED);
        } else {
            try {
                Request req = requestService.postNewRequest(input.notes, input.products);
                return new ResponseEntity(req, HttpStatus.CREATED);
            } catch (DataAccessException e){
                System.err.println(new Date() + " " + e.getMessage());
                return new ResponseEntity(Responses.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @RequestMapping(value="/{requestId}", method=RequestMethod.GET)
    public ResponseEntity<Request> getRequestById(@PathVariable long requestId) {
        try {
            Request r = requestService.getRequestById(requestId);
            return new ResponseEntity(r, HttpStatus.OK);
        } catch (DataRetrievalFailureException e) {
            return new ResponseEntity(Responses.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{requestId}", method=RequestMethod.PUT)
    public ResponseEntity<Request> putNewRequestStatus(@PathVariable long requestId, @RequestBody PutIdBodyInput input) {
        if (input.getStatusCode() == null) {
            return new ResponseEntity(Responses.MISSING_REQUIRED_PARAMETER, HttpStatus.BAD_REQUEST);
        } else {
            try {
                Request req = requestService.putNewRequestStatus(requestId, input.getStatusCode());
                return new ResponseEntity(req, HttpStatus.OK);
            } catch (DataRetrievalFailureException e) {
                return new ResponseEntity(Responses.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                System.err.println(new Date() + " " + e.getMessage());
                return new ResponseEntity(Responses.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     *  Payload template for POST /requests
     */
    @Data
    private static class PostBodyInput {        // static class required to work properly for jackson
        String notes;
        String[] products;
    }

    /**
     * Payload template for PUT /requests/{id}
     */
    @Data
    private static class PutIdBodyInput {
        String statusCode;
    }
}