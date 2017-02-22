package com.coastcapitalsavings.mvc.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.models.Request;
import com.coastcapitalsavings.mvc.models.RequestProduct;
import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database access object for the requests endpoint
 */
@Repository
public class RequestRepository {

    JdbcTemplate jdbcTemplate;

    // Store stored procedures so that they don't have to be recompiled for every use;
    PostNewRequestStoredProc postNewRequestStoredProc;
    PutRequestNewStatusId putRequestNewStatusIdStoredProc;

    @Autowired
    /*
    Tricky:  Need to do this instead of autowiring the Jdbc template so that we can ensure that the
    template is up before the stored procedures are initialized.
     */
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        postNewRequestStoredProc = new PostNewRequestStoredProc();
        putRequestNewStatusIdStoredProc = new PutRequestNewStatusId();
    }


    /**
     * Handles request posts by invoking a stored procedure
     * @param reqToPost request object to post without id set
     * @return posted request object without products set, with request status set to pending.
     */
    public Request postNewRequest(Request reqToPost) {
        return postNewRequestStoredProc.execute(reqToPost);
    }

    public Request putRequestNewStatusId(long reqId, int statusId) {
        return putRequestNewStatusIdStoredProc.execute(reqId, statusId);
    }


    private class PutRequestNewStatusId extends StoredProcedure {

        private static final String procName = "req_request_updateStatus";

        private PutRequestNewStatusId() {
            super(jdbcTemplate, procName);
            declareParameter(new SqlInOutParameter("inout_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("inout_status_id", Types.INTEGER));
            declareParameter(new SqlOutParameter("out_notes", Types.VARCHAR));
            declareParameter(new SqlOutParameter("out_dateCreated", Types.TIMESTAMP));
            declareParameter(new SqlOutParameter("out_submittedBy_id", Types.INTEGER));
            declareParameter(new SqlOutParameter("out_lastModified", Types.TIMESTAMP));
            declareParameter(new SqlOutParameter("out_lastModifiedBy_id", Types.INTEGER));
            compile();
        }

        private Request execute(long reqId, int statusId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_id", reqId);
            inputs.put("inout_status_id", statusId);
            Map<String, Object> outputs= execute(inputs);
            return mapResponseToRequest(outputs);
        }

        private Request mapResponseToRequest(Map<String, Object> responseMap) {
            try {
                Request req = new Request();
                req.setId((long) responseMap.get("inout_id"));
                req.setRequestStatus_id((long) responseMap.get("inout_status_id"));
                req.setNotes((String) responseMap.get("out_notes"));
                req.setDateCreated((Timestamp) responseMap.get("out_dateCreated"));
                req.setSubmittedBy_employeeId((int) responseMap.get("out_submittedBy_id"));
                req.setDateModified((Timestamp) responseMap.get("out_lastModified"));
                req.setLastModifiedBy_employeeId((int) responseMap.get("out_lastModifiedBy_id"));

                return req;

            } catch (ClassCastException e) {
                System.err.println("Class cast exception in PutRequestNewStatusId.mapResponseToRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }

    /**
     * Responsible for calling the stored procedure used to create a request in the database.
     * The input request object should NOT have an id set as it will be overwritten by the
     * database using autoincrement.
     */
    private class PostNewRequestStoredProc extends StoredProcedure {
        private static final String procName = "req_requests_insert";

        private PostNewRequestStoredProc() {
            super(jdbcTemplate, procName);
            declareParameter(new SqlInOutParameter("inout_notes", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("inout_dateCreated", Types.TIMESTAMP));
            declareParameter(new SqlInOutParameter("inout_submittedBy_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("inout_lastModified", Types.TIMESTAMP));
            declareParameter(new SqlInOutParameter("inout_lastModifiedBy_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("inout_status_id", Types.INTEGER));
            declareParameter(new SqlOutParameter("out_id", Types.INTEGER));
            compile();
        }

        /**
         * Perform the stored procedure to post a request.
         * @param req Request object to post.  The id value should be null as it will be
         *            set by the database using autoincrement.
         * @return posted Request object with id set.
         */
        private Request execute(Request req) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_notes", req.getNotes());
            inputs.put("inout_dateCreated", req.getDateCreated());
            inputs.put("inout_submittedBy_id", req.getSubmittedBy_employeeId());
            inputs.put("inout_lastModified", req.getDateModified());
            inputs.put("inout_lastModifiedBy_id", req.getLastModifiedBy_employeeId());
            inputs.put("inout_status_id", req.getRequestStatus_id());

            Map<String, Object> outputs= execute(inputs);

            return mapResponseToRequest(outputs);
        }

        /**
         * Parse out a new Request object from a HashMap
         * @param responseMap Keys and values from the stored procedure response
         * @return new Request object with id set
         */
        private Request mapResponseToRequest(Map<String, Object> responseMap) {
            try {
                Request req = new Request();
                req.setId((long)responseMap.get("out_id"));
                req.setNotes((String)responseMap.get("inout_notes"));
                req.setDateCreated((Timestamp)responseMap.get("inout_dateCreated"));
                req.setSubmittedBy_employeeId((int)responseMap.get("inout_submittedBy_id"));
                req.setDateModified((Timestamp)responseMap.get("inout_lastModified"));
                req.setLastModifiedBy_employeeId((int)responseMap.get("inout_lastModifiedBy_id"));
                req.setRequestStatus_id((long)responseMap.get("inout_status_id"));
                return req;
            } catch (ClassCastException e) {
                System.err.println("Class cast exception in addProductToRequest.mapResponseToRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }
}
