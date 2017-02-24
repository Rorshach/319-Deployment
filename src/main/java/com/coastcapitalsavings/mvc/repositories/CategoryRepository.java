package com.coastcapitalsavings.mvc.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coastcapitalsavings.mvc.models.Category;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for categories endpoint
 */
@Repository
public class CategoryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

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
}
