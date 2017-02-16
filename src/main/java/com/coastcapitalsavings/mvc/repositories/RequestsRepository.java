package com.coastcapitalsavings.mvc.repositories;

import com.coastcapitalsavings.mvc.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Collection;

@org.springframework.stereotype.Repository
public interface RequestsRepository extends JpaRepository<Request, Integer> {
}
