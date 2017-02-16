package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Category;

import com.coastcapitalsavings.mvc.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Categories controller handles all REST actions related to the /categories resource.
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesRepository repo;

    /**
     * Get all categories on a GET request to the root resources
     * @return JSON representation of all categories in the database
     */
    @RequestMapping(method= RequestMethod.GET)
    public Iterable<Category> getAllCategories() {
        return repo.findAll();
    }
}
