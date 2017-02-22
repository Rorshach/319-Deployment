package com.coastcapitalsavings.mvc.repositories;



import com.coastcapitalsavings.mvc.models.Employee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




/**
 * Data access object for login endpoint
 */

public class AuthenticationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public UsernamePasswordAuthenticationToken authenticateUser(String userEmail, String password) throws DataAccessException {
        String query = "call req_categories_lookupEmployeeByEmail";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Employee>() {

            @Override
            public Employee doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = ps.executeQuery();
            }
        });
    }



}



