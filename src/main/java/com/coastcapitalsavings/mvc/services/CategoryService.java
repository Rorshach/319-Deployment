package com.coastcapitalsavings.mvc.services;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepo;

    public Category getCategoryById(long categoryId) {
        Category c = categoryRepo.getCategoryById(categoryId);
        return c;
    }
}
