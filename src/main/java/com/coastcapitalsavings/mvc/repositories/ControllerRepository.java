package com.coastcapitalsavings.mvc.repositories;

import com.coastcapitalsavings.mvc.models.Category;
import com.coastcapitalsavings.mvc.models.Responses.CategoriesAllResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2017-02-20.
 */
@Repository
public class ControllerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CategoriesAllResponse> getAllCategories() {
        String query = "call req_categories_getAll";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<List<CategoriesAllResponse>>() {

            @Override
            public List<CategoriesAllResponse> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = ps.executeQuery();
                RowMapper<CategoriesAllResponse> rsm = new CategoriesRowMapper();

                List<CategoriesAllResponse> catList = new ArrayList<>();
                while (rs.next()) {
                    CategoriesAllResponse c = rsm.mapRow(rs, rs.getRow());
                    catList.add(c);
                }
                return catList;

            }
        });
    }

    class CategoriesRowMapper implements RowMapper<CategoriesAllResponse> {

        @Override
        public CategoriesAllResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoriesAllResponse c = new CategoriesAllResponse();
            c.setCid(rs.getInt("id"));
            c.setName(rs.getString("name"));
            return c;
        }
    }
}
