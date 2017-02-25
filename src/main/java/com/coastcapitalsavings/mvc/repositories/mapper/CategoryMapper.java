package com.coastcapitalsavings.mvc.repositories.mapper;

import com.coastcapitalsavings.mvc.models.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category c = new Category();
        c.setCode(rs.getString("categoryCode"));
        c.setDescription(rs.getString("categoryDescription"));
        return c;
    }
}