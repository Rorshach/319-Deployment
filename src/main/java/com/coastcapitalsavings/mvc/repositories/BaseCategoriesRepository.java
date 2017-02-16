package com.coastcapitalsavings.mvc.repositories;

import java.io.Serializable;
import java.util.Set;

import com.coastcapitalsavings.mvc.models.Category;
import org.springframework.data.repository.Repository;

/**
 * Categories base repository restricts the available methods to only findAll().  The c
 */
@org.springframework.stereotype.Repository
public interface BaseCategoriesRepository<T, ID extends Serializable> extends Repository<Category, Integer> {
    /*
     * Extending the default spring repository allows us to only expose verbs that we want.  The actual
     * repository will extend this class, and have only methods declared by this interface available to
     * itself.
     */
    Set<Category> findAll();
    Category findOne(Integer primaryKey);
}
