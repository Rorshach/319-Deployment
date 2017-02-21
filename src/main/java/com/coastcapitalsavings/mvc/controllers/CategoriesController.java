package com.coastcapitalsavings.mvc.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;
import com.coastcapitalsavings.mvc.repositories.ControllerRepository;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

/**
 * Categories controller handles all REST actions related to the /categories resource.
 */
@RestController
@RequestMapping("/categories")

public class CategoriesController {

    @Autowired
    ControllerRepository controllerRepo;

    @JsonView(ModelViews.Summary.class)
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> response = controllerRepo.getAllCategories();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            System.err.println(new Date() + " " +  e.getMessage());     // TODO:  Can be logged by a logger
            return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
