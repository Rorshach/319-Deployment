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

    // Store procedures so that they don't have to be recompiled for every use;
    PostNewRequestStoredProc postNewRequestStoredProc;
    AddProductToRequestStoredProc addProductToRequestStoredProc;

    @Autowired
    /*
    Tricky:  Need to do this instead of autowiring the Jdbc template so that we can ensure that the
    template is up before the stored procedures are initialized.
     */
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        postNewRequestStoredProc = new PostNewRequestStoredProc();
        addProductToRequestStoredProc = new AddProductToRequestStoredProc();
    }


    /**
     * Handles request posts by invoking a stored procedure
     * @param reqToPost request object to post without id set
     * @return posted request object without products set, with request status set to pending.
     */
    public Request postNewRequest(Request reqToPost) {
        return postNewRequestStoredProc.execute(reqToPost);

    }

    /**
     * After the request has been made, populates the products_requests many to many join table
     * and adds these products with pending status to the request.
     *
     * @param id A valid product id
     * @param postedReq A request object, with id set
     * @return Updated request object containing that product with a product status of pending.
     */
    public Request addProductToRequest(int id, Request postedReq) {
        return addProductToRequestStoredProc.execute(id, postedReq);
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

    /**
     * Responsible for calling the stored procedure to update the products_requests table and populating
     * Requests with RequestProducts;
     */
    private class AddProductToRequestStoredProc extends StoredProcedure {
        private static final String procName = "req_productInRequest_insert";

        private AddProductToRequestStoredProc() {
            super(jdbcTemplate, procName);
            declareParameter(new SqlParameter("in_request_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("inout_product_id", Types.INTEGER));
            declareParameter(new SqlInOutParameter("inout_product_status_id", Types.INTEGER));
            declareParameter(new SqlOutParameter("out_product_name", Types.VARCHAR));
            compile();
        }

        /**
         * Perform the stored procedure to add a list of RequestProducts to a request.  At this point
         * request should have an id set, but not a list of requested products
         * @param prodId Id number of product to add.
         * @param req Request object to put requested products in
         * @return Request with RequestedProduct in it.
         */
        private Request execute(int prodId, Request req) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_request_id", req.getId());
            inputs.put("inout_product_id", prodId);
            inputs.put("inout_product_status_id", 1);       // TODO Hardcoded product status value until enum set

            Map<String, Object> outputs = execute(inputs);

            return addRequestProductToUpdatedRequest(outputs, req);
        }

        /**
         * Given a map containing a product and product status info, make each pair into a RequestProduct
         * and add it to the Request.  The requested products are all set to pending.
         * @param responseMap HashMap containing response information.
         * @param req Request object to have products added to.
         * @return updated Request object
         */
        private Request addRequestProductToUpdatedRequest(Map<String, Object> responseMap, Request req) {
            try {
                Product p = new Product();
                p.setId((long) responseMap.get("inout_product_id"));
                p.setName((String) responseMap.get("out_product_name"));

                long pStatusId = (long) responseMap.get("inout_product_status_id");

                RequestProduct rp = new RequestProduct();
                rp.setProduct(p);
                rp.setProductStatus_id(pStatusId);

                if (req.getProducts() == null) {        // If Request.products has not been initialized yet, do it now
                    List<RequestProduct> prodList = new ArrayList<>();
                    prodList.add(rp);
                    req.setProducts(prodList);
                } else {
                    req.getProducts().add(rp);
                }
                return req;

            } catch (ClassCastException e) {
                System.err.println("Class cast exception in AddProductToRequestStoredProc.addRequestProductToUpdatedRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }
}
