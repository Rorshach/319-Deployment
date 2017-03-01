package com.coastcapitalsavings.mvc.repositories;


import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import com.coastcapitalsavings.mvc.models.Category;

import org.springframework.dao.DataAccessException;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data access object for categories endpoint
 */
@Repository
public class CategoryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    GetCategoryByIdStoredProc getCategoryByIdStoredProc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        getCategoryByIdStoredProc = new GetCategoryByIdStoredProc();
    }

    public Category getCategoryById(String categoryCode) {
        return getCategoryByIdStoredProc.execute(categoryCode);
    }

    public List<Category> getAllCategories() throws DataAccessException {
        String query = "call req_category_lookupAll";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<List<Category>>() {

            @Override
            public List<Category> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = ps.executeQuery();
                RowMapper<Category> rsm = new CategoriesRowMapper();

                List<Category> catList = new ArrayList<>();
                while (rs.next()) {
                    Category c = rsm.mapRow(rs, rs.getRow());
                    catList.add(c);
                }
                return catList;

            }
        });
    }

    private static class CategoriesRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category c = new Category();
            c.setCode(rs.getString("categoryCode"));
            c.setDescription(rs.getString("categoryDescription"));
            return c;
        }
    }

    private class GetCategoryByIdStoredProc extends StoredProcedure {
        private static final String procName = "req_category_lookupById";

        private GetCategoryByIdStoredProc() {
            super(jdbcTemplate, procName);
            declareParameter(new SqlInOutParameter("inout_categoryCode", Types.VARCHAR));
            declareParameter(new SqlOutParameter("out_categoryDescription", Types.VARCHAR));
            compile();
        }

        private Category execute(String categoryCode) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_categoryCode", categoryCode);

            Map<String, Object> outputs = execute(inputs);
            return mapResponseToCategory(outputs);
        }

        private Category mapResponseToCategory(Map<String, Object> responseMap) {
            try {
                Category c = new Category();
                c.setCode((String) responseMap.get("inout_categoryCode"));
                c.setDescription((String)responseMap.get("out_categoryDescription"));
                return c;
            } catch (ClassCastException e) {
                System.err.println("Class cast exception in getCategoryById.mapResponseToRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }
}
