package com.coastcapitalsavings.mvc.repositories;

import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    JdbcTemplate jdbcTemplate;



    public Optional<Employee> getUser(String userEmail) throws DataAccessException{

        String query = "call req_employees_lookupEmployeeByEmail";
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
            e.setId(rs.getInt("out_employee_id"));
            e.setFName(rs.getString("out_employee_fName"));
            e.setLName(rs.getString("out_employee_lName"));
            e.setEmail(rs.getString("inout_employee_email"));
            e.setPassword(rs.getString("out_employee_pwd"));
            Role r = new Role(rs.getInt("out_employee_role"));
            e.setRole(r);
            return e;
        }
    }

    private class PostNewRequestStoredProc extends StoredProcedure {

    }
}

