package com.coastcapitalsavings.mvc.repositories;


import com.coastcapitalsavings.mvc.models.RequestStatus;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface RequestStatusRepository extends CrudRepository<RequestStatus, Integer> {
}
