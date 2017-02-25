package com.coastcapitalsavings.mvc.controllers;



import com.coastcapitalsavings.mvc.services.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;
import com.coastcapitalsavings.mvc.repositories.CategoryRepository;
import com.coastcapitalsavings.util.Responses;

import java.util.Date;
import java.util.List;

/**
 * Categories controller handles all REST actions related to the /categories resource.
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoryRepository controllerRepo;

    @Autowired
    CategoryService categoryService;

    @JsonView(ModelViews.Summary.class)
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> response = controllerRepo.getAllCategories();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            System.err.println(new Date() + " " +  e.getMessage());     // TODO:  Can be logged by a logger
            return new ResponseEntity(Responses.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/{categoryId}", method=RequestMethod.GET)
    public ResponseEntity<Category> getCategoryById(@PathVariable long categoryId) {
        try {
            Category c = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<Category>(c, HttpStatus.OK);
        } catch (DataRetrievalFailureException e) {
            return new ResponseEntity(Responses.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
