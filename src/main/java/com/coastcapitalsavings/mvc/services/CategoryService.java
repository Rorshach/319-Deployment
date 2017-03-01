package com.coastcapitalsavings.mvc.services;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.repositories.CategoryRepository;
import com.coastcapitalsavings.mvc.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    ProductRepository productRepo;

    public Category getCategoryById(String categoryCode) {
        Category c = categoryRepo.getCategoryById(categoryCode);
        List<Product> products = productRepo.getProductsInCategoryByCategoryCode(categoryCode);
        c.setProducts(products);
        return c;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.getAllCategories();
    }
}
