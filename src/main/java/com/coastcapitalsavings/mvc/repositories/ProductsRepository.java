package com.coastcapitalsavings.mvc.repositories;

import com.coastcapitalsavings.mvc.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Product, Integer> {
}
