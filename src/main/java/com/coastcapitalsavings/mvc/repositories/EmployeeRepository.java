package com.coastcapitalsavings.mvc.repositories;

import com.coastcapitalsavings.mvc.models.Employee;
import com.coastcapitalsavings.mvc.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<Employee> getUser(String userEmail) throws DataAccessException{
        PostLookupEmployeeByEmailStoredProc storedProc = new PostLookupEmployeeByEmailStoredProc();
        return storedProc.execute(userEmail);
    }

    private class PostLookupEmployeeByEmailStoredProc extends StoredProcedure {
        private static final String procName = "req_employees_lookupEmployeeByEmail";

        private PostLookupEmployeeByEmailStoredProc() {
            super(jdbcTemplate, procName);
            declareParameter(new SqlInOutParameter("out_employee_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("out_employee_fName", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("out_employee_lName", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("inout_employee_email", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("out_employee_pwd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("out_employee_role", Types.INTEGER));
            compile();
        }

        /**
         * Perform the stored procedure to post user credentials.
         *
         * @param userEmail Request object to post.  The id value should be null as it will be
         *                  set by the database using autoincrement.
         * @return Valid employee with credentials matching the posted one, and the person's role
         */
        private Optional<Employee> execute(String userEmail) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("out_employee_id", 1);
            inputs.put("out_employee_fName", "june");
            inputs.put("out_employee_lName", "m");
            inputs.put("inout_employee_email", userEmail);
            inputs.put("out_employee_pwd", "");
            inputs.put("out_employee_role", 2);
            Map<String, Object> outputs = execute(inputs);
            return mapResponseToEmployee(outputs);
        }
        private Optional<Employee> mapResponseToEmployee(Map<String, Object> responseMap) {
            try {
                Employee e = new Employee();
                Integer id = (Integer) responseMap.get("out_employee_id");
                e.setId((long)id);
                e.setFName((String)responseMap.get("out_employee_fName"));
                e.setLName((String)responseMap.get("out_employee_lName"));
                e.setEmail((String)responseMap.get("inout_employee_email"));
                e.setPassword((String)responseMap.get("out_employee_pwd"));
                Role r = new Role((long)responseMap.get("out_employee_role"));
                e.setRole(r);
                Optional <Employee> oe = Optional.of(e);
                return oe;
            }
            catch (ClassCastException e) {
                System.err.println("Class cast exception in addProductToRequest.mapResponseToRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }

        }
        }

}

