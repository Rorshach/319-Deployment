package com.coastcapitalsavings.mvc.repositories;


import com.coastcapitalsavings.mvc.models.Employee;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface EmployeesRepository extends CrudRepository<Employee, Integer> {
    // TODO:  Methods here will need to be hidden, see Categories repo and BaseCategories repo
    // for an example
}
