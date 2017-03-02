package com.coastcapitalsavings.mvc.repositories.mapper;

import com.coastcapitalsavings.mvc.models.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps result sets objects to requests on a per row basis.
 */
public class RequestSummaryMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request r = new Request();
        r.setId(rs.getLong("requestId"));
        r.setDateCreated(rs.getTimestamp("dateCreated"));
        r.setSubmittedBy(rs.getString("submittedBy"));
        r.setDateModified(rs.getTimestamp("lastModified"));
        r.setLastModifiedBy(rs.getString("lastModifiedBy"));
        r.setStatusCode(rs.getString("statusCode"));
        return r;
    }
}
