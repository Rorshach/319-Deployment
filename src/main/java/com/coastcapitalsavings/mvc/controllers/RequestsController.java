package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.repositories.CategoriesRepository;
import com.coastcapitalsavings.mvc.repositories.RequestsRepository;
import com.coastcapitalsavings.mvc.services.RequestsService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsRepository requestsRepository;
    private CategoriesRepository categoriesRepository;

    private int firstElement = 0;
    @RequestMapping(method = RequestMethod.GET)
    public List<Request> getRequests() {
        return requestsRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Request postRequest(JsonArray json) {
        //TODO:
        //Front-End, I assume the data is being passed in as a JsonArray. Modify as needed.
        Request request = new Request();

        //Unused because I'm unsure about how the category is necessary here if the JsonArray contains the requested products.
        int categoryId = Integer.valueOf(json.get(firstElement).toString());
        Category c = categoriesRepository.findOne(categoryId);

        //TODO: Create the productsRepository
//        String[] productIds = json.get(firstElement + 1).toString());
//        Product p1 = productsRepository(Integer.valueOf(products[0]));
//        Product p2 = productsRepository(Integer.valueOf(products[0]));
//        Product p3 = productsRepository(Integer.valueOf(products[0]));
//
//        List<Product> products = new ArrayList<Product>();
//        products.add(p1);
//        products.add(p2);
//        products.add(p3);

        //TODO: Need to add the products to the Request. Something like
        // request.setProducts(products);

        return request;
    }

}