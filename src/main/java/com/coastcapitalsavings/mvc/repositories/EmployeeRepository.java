package com.coastcapitalsavings.mvc.repositories;

import com.coastcapitalsavings.mvc.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmployeeRepository {
    private Employee e;

    @Autowired
    DataSource datasource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    protected Optional<Employee> getUser(String userEmail) throws DataAccessException{
        //TODO: hardcoded with * to test, need to change to stored procedure and align with cc standards
        String query = "SELECT fName, lName FROM cpsc319.employees WHERE email = " + userEmail;
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Optional<Employee>>() {

                @Override
                public Optional<Employee> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                    ResultSet rs = ps.executeQuery();
                    RowMapper<Employee> rsm = new EmployeeRowMapper();
                    Employee e = rsm.mapRow(rs, rs.getRow());
                    Optional <Employee> oe = Optional.of(e);
                    return oe;
                }
            });

    }
    private static class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee e = new Employee();
            e.setFName(rs.getString("fName"));
            e.setLName(rs.getString("lName"));
            return e;
        }
    }
}

