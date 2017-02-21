package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.Responses.CategoriesAllResponse;
import com.coastcapitalsavings.mvc.repositories.ControllerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Categories controller handles all REST actions related to the /categories resource.
 */
@RestController
@RequestMapping("/categories")

public class CategoriesController {

    @Autowired
    ControllerRepository controllerRepo;

    @RequestMapping(method= RequestMethod.GET)
    public List<CategoriesAllResponse> getAllCategories() {
        return controllerRepo.getAllCategories();
    }
}
