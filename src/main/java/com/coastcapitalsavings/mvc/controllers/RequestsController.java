package com.coastcapitalsavings.mvc.controllers;

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

import java.util.Date;

/**
 * Handles all requests to the requests resource
 */
@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    RequestService requestService;

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