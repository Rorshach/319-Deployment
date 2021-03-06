package com.coastcapitalsavings.mvc.repositories;


import com.coastcapitalsavings.mvc.repositories.mapper.RequestSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.coastcapitalsavings.mvc.models.Request;
import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Timestamp;
import java.sql.Types;
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
    GetRequestByIdStoredProc getRequestByIdStoredProc;
    PostNewRequestStoredProc postNewRequestStoredProc;
    PutRequestNewStatusIdStoredProc putRequestNewStatusIdStoredProc;
    CheckRequestExistsStoredProc checkRequestExistsStoredProc;
    GetRequestsBetweenDateRangeStoredProc getRequestBetweenDateRangeStoredProc;

    @Autowired
    /*
    Tricky:  Need to do this instead of autowiring the Jdbc template so that we can ensure that the
    template is up before the stored procedures are initialized.
     */
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        getRequestByIdStoredProc = new GetRequestByIdStoredProc();
        postNewRequestStoredProc = new PostNewRequestStoredProc();
        putRequestNewStatusIdStoredProc = new PutRequestNewStatusIdStoredProc();
        checkRequestExistsStoredProc = new CheckRequestExistsStoredProc();
        getRequestBetweenDateRangeStoredProc = new GetRequestsBetweenDateRangeStoredProc();
    }

    /**
     * Verifies if there is a request record with a particular id in the database
     * @param reqId id of Request to verify
     * @return true if exists, false otherwise
     */
    public Boolean checkRequestExists(long reqId) {
        return checkRequestExistsStoredProc.execute(reqId);
    }


    public Request getRequestById(long reqId) {
        return getRequestByIdStoredProc.execute(reqId);
    }
    /**
     * Handles request posts by invoking a stored procedure
     * @param reqToPost request object to post without id set
     * @return posted request object without products set, with request status set to pending.
     */
    public Request postNewRequest(Request reqToPost) {
        return postNewRequestStoredProc.execute(reqToPost);
    }


    public Request putRequestNewStatusId(long reqId, String statusCode) {
        return putRequestNewStatusIdStoredProc.execute(reqId, statusCode);
    }

    public List<Request> getRequestsByDateRange(Timestamp tsFrom, Timestamp tsTo) {
        return getRequestBetweenDateRangeStoredProc.execute(tsFrom, tsTo);
    }


    private class CheckRequestExistsStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_request_lookupExists";

        private CheckRequestExistsStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_requestId", Types.BIGINT));
            declareParameter(new SqlOutParameter("out_exists", Types.BOOLEAN));
            compile();
        }

        private boolean execute(long reqId) {
            HashMap<String, Object> inputs = new HashMap<>();
            inputs.put("in_requestId",reqId);

            Map<String, Object> outputs = execute(inputs);
            return (boolean) outputs.get("out_exists");
        }
    }

    private class GetRequestByIdStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_request_lookupById";

        private GetRequestByIdStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlInOutParameter("inout_requestId", Types.BIGINT));
            declareParameter(new SqlOutParameter("out_notes", Types.VARCHAR));
            declareParameter(new SqlOutParameter("out_dateCreated", Types.TIMESTAMP));
            declareParameter(new SqlOutParameter("out_submittedBy", Types.CHAR));
            declareParameter(new SqlOutParameter("out_lastModified", Types.TIMESTAMP));
            declareParameter(new SqlOutParameter("out_lastModifiedBy", Types.CHAR));
            declareParameter(new SqlOutParameter("out_statusCode", Types.CHAR));
            compile();
        }

        /**
         * Perform the stored procedure to get a request.
         * @param reqId Request object to get.
         * @return Request object with the reqId.
         */
        private Request execute(long reqId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_requestId", reqId);

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
                req.setId((long)responseMap.get("inout_requestId"));
                req.setNotes((String)responseMap.get("out_notes"));
                req.setDateCreated((Timestamp)responseMap.get("out_dateCreated"));
                req.setSubmittedBy((String)responseMap.get("out_submittedBy"));
                req.setDateModified((Timestamp)responseMap.get("out_lastModified"));
                req.setLastModifiedBy((String)responseMap.get("out_lastModifiedBy"));
                req.setStatusCode((String)responseMap.get("out_statusCode"));
                return req;
            } catch (ClassCastException e) {
                System.err.println("Class cast exception in getRequestById.mapResponseToRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }

    private class PutRequestNewStatusIdStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_request_updateStatus";

        private PutRequestNewStatusIdStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlInOutParameter("inout_requestId", Types.BIGINT));
            declareParameter(new SqlInOutParameter("inout_inout_statusCode", Types.CHAR));
            declareParameter(new SqlOutParameter("out_notes", Types.VARCHAR));
            declareParameter(new SqlOutParameter("out_dateCreated", Types.TIMESTAMP));
            declareParameter(new SqlOutParameter("out_submittedBy", Types.CHAR));
            declareParameter(new SqlOutParameter("out_lastModified", Types.TIMESTAMP));
            declareParameter(new SqlOutParameter("out_lastModifiedBy", Types.CHAR));
            compile();
        }

        private Request execute(long reqId, String statusCode) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_requestId", reqId);
            inputs.put("inout_inout_statusCode", statusCode);
            Map<String, Object> outputs = execute(inputs);
            return mapResponseToRequest(outputs);
        }

        private Request mapResponseToRequest(Map<String, Object> responseMap) {
            try {
                Request req = new Request();
                req.setId((long) responseMap.get("inout_requestId"));
                req.setStatusCode((String) responseMap.get("inout_inout_statusCode"));
                req.setNotes((String) responseMap.get("out_notes"));
                req.setDateCreated((Timestamp) responseMap.get("out_dateCreated"));
                req.setSubmittedBy((String) responseMap.get("out_submittedBy"));
                req.setDateModified((Timestamp) responseMap.get("out_lastModified"));
                req.setLastModifiedBy((String) responseMap.get("out_lastModifiedBy"));

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
        private static final String PROC_NAME = "req_request_insert";

        private PostNewRequestStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlInOutParameter("inout_notes", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("inout_dateCreated", Types.TIMESTAMP));
            declareParameter(new SqlInOutParameter("inout_submittedBy", Types.CHAR));
            declareParameter(new SqlInOutParameter("inout_lastModified", Types.TIMESTAMP));
            declareParameter(new SqlInOutParameter("inout_lastModifiedBy", Types.CHAR));
            declareParameter(new SqlInOutParameter("inout_statusCode", Types.CHAR));
            declareParameter(new SqlOutParameter("out_requestId", Types.BIGINT));
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
            inputs.put("inout_submittedBy", req.getSubmittedBy());
            inputs.put("inout_lastModified", req.getDateModified());
            inputs.put("inout_lastModifiedBy", req.getLastModifiedBy());
            inputs.put("inout_statusCode", req.getStatusCode());

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
                req.setId((long) responseMap.get("out_requestId"));
                req.setNotes((String) responseMap.get("inout_notes"));
                req.setDateCreated((Timestamp) responseMap.get("inout_dateCreated"));
                req.setSubmittedBy((String) responseMap.get("inout_submittedBy"));
                req.setDateModified((Timestamp) responseMap.get("inout_lastModified"));
                req.setLastModifiedBy((String) responseMap.get("inout_lastModifiedBy"));
                req.setStatusCode((String) responseMap.get("inout_statusCode"));
                return req;
            } catch (ClassCastException e) {
                System.err.println("Class cast exception in addProductToRequest.mapResponseToRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }

    private class GetRequestsBetweenDateRangeStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_request_lookUpByDateRange";

        private GetRequestsBetweenDateRangeStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_fromDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter("in_toDate", Types.TIMESTAMP));
            declareParameter(new SqlReturnResultSet("requests", new RequestSummaryMapper()));
            compile();
        }

        private List<Request> execute(Timestamp from, Timestamp to) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_fromDate", from);
            inputs.put("in_toDate", to);

            Map<String, Object> outputs = execute(inputs);
            return (List<Request>) outputs.get("requests");
        }
    }
}