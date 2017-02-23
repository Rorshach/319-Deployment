package com.coastcapitalsavings.mvc.repositories;


import com.coastcapitalsavings.mvc.models.Profile;
import com.coastcapitalsavings.mvc.repositories.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.models.RequestProduct;
import com.coastcapitalsavings.mvc.repositories.mapper.RequestProductMapper;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    JdbcTemplate jdbcTemplate;

    GetRequestProductsInRequestStoredProc getRequestProductsInRequestStoredProc;
    AddProductToRequestStoredProc addProductToRequestStoredProc;
    GetProductInProfileStoredProc getProductInProfileStoredProc;

    /*
    Tricky:  Need to do this instead of autowiring the Jdbc template so that we can ensure that the
    template is up before the stored procedures are initialized.
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        getRequestProductsInRequestStoredProc = new GetRequestProductsInRequestStoredProc();
        addProductToRequestStoredProc = new AddProductToRequestStoredProc();
        getProductInProfileStoredProc = new GetProductInProfileStoredProc();
    }

    public List<RequestProduct> getRequestProductsinRequestByRequestId(long reqId) {
        return getRequestProductsInRequestStoredProc.execute(reqId);
    }

    public RequestProduct addProductToRequest(long prodId, long reqId) {
        return addProductToRequestStoredProc.execute(prodId, reqId);
    }

    public List<Product> getProductsInProfileByProfileId(long profileId) {
        return getProductInProfileStoredProc.execute(profileId);
    }

    private class GetProductInProfileStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_productInProfile_lookupByProductId";

        private GetProductInProfileStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_profile_id", Types.INTEGER));
            declareParameter(new SqlReturnResultSet("products", new ProductMapper()));
            compile();
        }

        private List<Product> execute(long profileId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_profile_id", profileId);

            Map<String, Object> outputs = execute(inputs);
            return (List<Product>) outputs.get("products");
        }
    }


    private class GetRequestProductsInRequestStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_productInRequest_lookupByRequestId";

        private GetRequestProductsInRequestStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_request_id", Types.INTEGER));
            declareParameter(new SqlReturnResultSet("products", new RequestProductMapper()));
            compile();
        }

        private List<RequestProduct> execute(long reqId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_request_id", reqId);

            Map<String, Object> outputs = execute(inputs);
            return (List<RequestProduct>) outputs.get("products");
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
         * Associates a product with a request and returns a RequestProduct object
         * @param prodId Id number of Product to add.
         * @param reqId Id number of the Request being added to
         * @return Request with RequestedProduct in it.
         */
        private RequestProduct execute(long prodId, long reqId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_request_id", reqId);
            inputs.put("inout_product_id", prodId);
            inputs.put("inout_product_status_id", 1);       // TODO Hardcoded product status value until enum set

            Map<String, Object> outputs = execute(inputs);

            return mapResponseToRequestProduct(outputs);
        }

        /**
         * Given a map containing a product and product status info, make a product and
         * add it to a RequestProduct with a ProductStatus id, then return that RequestProduct
         * @param responseMap HashMap containing response information.
         * @return updated Request object
         */
        private RequestProduct mapResponseToRequestProduct(Map<String, Object> responseMap) {
            try {
                Product p = new Product();
                p.setId((long) responseMap.get("inout_product_id"));
                p.setName((String) responseMap.get("out_product_name"));

                long pStatusId = (long) responseMap.get("inout_product_status_id");

                RequestProduct rp = new RequestProduct();
                rp.setProduct(p);
                rp.setProductStatus_id(pStatusId);

                return rp;

            } catch (ClassCastException e) {
                System.err.println("Class cast exception in AddProductToRequestStoredProc.addRequestProductToUpdatedRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }
}
