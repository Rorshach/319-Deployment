package com.coastcapitalsavings.mvc.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;

/**
 * Stored procedure for interfacing with req_categories_getAll
 */
public class Req_StoredProcedure extends StoredProcedure {

    public Req_StoredProcedure (JdbcTemplate template, String name) {
        super (template, name);
        // May need some variable setting here
        setFunction(false);
    }

}
