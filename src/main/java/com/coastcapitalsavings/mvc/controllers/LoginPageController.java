package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.repositories.AuthenticationRepository;
//import com.coastcapitalsavings.util.Responses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.soap.AddressingFeature;

/**
 * Controller for the request page
 */
@Controller
public class LoginPageController {

    @Autowired
    AuthenticationRepository controllerRepo;

    //@JsonView(ModelViews.Summary.class)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UsernamePasswordAuthenticationToken> authenticateUser(String userEmail, String password){
        try {
            UsernamePasswordAuthenticationToken authToken = controllerRepo.authenticateUser(userEmail, password);
            return new ResponseEntity(authToken,HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity(AddressingFeature.Responses.NON_ANONYMOUS.toString(), HttpStatus.UNAUTHORIZED);
            //TODO: change response type to util.Responses
        }
    }
}
