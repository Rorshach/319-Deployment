package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.repositories.RequestRepository;
import com.coastcapitalsavings.mvc.services.RequestService;
import com.coastcapitalsavings.util.Responses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    RequestRepository requestRepository;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Request>> getAllRequest() {
        try {
            List<Request> response = requestRepository.getAllRequests();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            System.err.println(new Date() + " " +  e.getMessage());     // TODO:  Can be logged by a logger
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

    @Data
    private static class PostBodyInput {        // static class required to work properly for jackson
        String notes;
        int[] products;
    }
}