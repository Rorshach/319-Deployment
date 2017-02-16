package com.coastcapitalsavings.mvc.controllers;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.repositories.CategoriesRepository;
import com.coastcapitalsavings.mvc.repositories.ProductsRepository;
import com.coastcapitalsavings.mvc.repositories.RequestsRepository;
import com.coastcapitalsavings.mvc.repositories.EmployeesRepository;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Request> getRequests() {
        return requestsRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Request postRequest(@RequestBody ProductsPost postReq) {
        Category cat = categoriesRepository.findOne(postReq.getCategory_id());
        Employee emp = employeesRepository.findOne(11);                            // TODO: hardcoded user.  Will need to be parameterized
        Product prod = productsRepository.findOne(postReq.getCategory_id());
        Request req = new Request(emp, prod);
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

        return req;

    }

    @Getter
    public class ProductsPost {
        private Integer category_id;
    }

}