package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.repositories.RequestsRepository;
import com.coastcapitalsavings.mvc.services.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsRepository requestsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Request> getRequests() {
        return requestsRepository.findAll();
    }


}
