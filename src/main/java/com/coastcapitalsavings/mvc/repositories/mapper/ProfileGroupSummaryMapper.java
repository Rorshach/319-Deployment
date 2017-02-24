package com.coastcapitalsavings.mvc.repositories.mapper;

import com.coastcapitalsavings.mvc.models.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates profiles without setting the products list
 */
public class ProfileGroupSummaryMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();
        profile.setCode(rs.getString("profileCode"));
        profile.setDescription(rs.getString("profileDescription"));
        return profile;
    }
}
