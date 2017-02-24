package com.coastcapitalsavings.mvc.repositories;


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

    public RequestProduct addProductToRequest(String productCode, long reqId) {
        return addProductToRequestStoredProc.execute(productCode, reqId);
    }

    public List<Product> getProductsInProfileByProfileCode(String profileCode) {
        return getProductInProfileStoredProc.execute(profileCode);
    }


    private class GetProductInProfileStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_productInProfileGroup_lookupByProfileGroupCode";

        private GetProductInProfileStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_profileCode", Types.VARCHAR));
            declareParameter(new SqlReturnResultSet("products", new ProductMapper()));
            compile();
        }

        private List<Product> execute(String profileCode) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_profileCode", profileCode);

            Map<String, Object> outputs = execute(inputs);
            return (List<Product>) outputs.get("products");
        }
    }

    private class GetRequestProductsInRequestStoredProc extends StoredProcedure {

        private static final String PROC_NAME = "req_productInRequest_lookupByRequestId";

        private GetRequestProductsInRequestStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_requestId", Types.BIGINT));
            declareParameter(new SqlReturnResultSet("products", new RequestProductMapper()));
            compile();
        }

        private List<RequestProduct> execute(long reqId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_requestId", reqId);

            Map<String, Object> outputs = execute(inputs);
            return (List<RequestProduct>) outputs.get("products");
        }
    }

    /**
     * Responsible for calling the stored procedure to add a product to a request
     */
    private class AddProductToRequestStoredProc extends StoredProcedure {
        private static final String procName = "req_productInRequest_insert";

        private AddProductToRequestStoredProc() {
            super(jdbcTemplate, procName);
            declareParameter(new SqlParameter("in_requestId", Types.BIGINT));
            declareParameter(new SqlInOutParameter("inout_productCode", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("inout_statusCode", Types.CHAR));
            declareParameter(new SqlOutParameter("out_productDescription", Types.VARCHAR));
            compile();
        }

        /**
         * Associates a product with a request and returns a RequestProduct object
         * @param productCode Code of the product to add.
         * @param reqId Id number of the Request being added to
         * @return Request with RequestedProduct in it.
         */
        private RequestProduct execute(String productCode, long reqId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_requestId", reqId);
            inputs.put("inout_productCode", productCode);
            inputs.put("inout_statusCode", "PEND");       // TODO Hardcoded product status value until enum set

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
                p.setCode((String) responseMap.get("inout_productCode"));
                p.setDescription((String) responseMap.get("out_productDescription"));

                String productStatusCode = (String) responseMap.get("inout_statusCode");

                RequestProduct rp = new RequestProduct();
                rp.setProduct(p);
                rp.setStatusCode(productStatusCode);

                return rp;

            } catch (ClassCastException e) {
                System.err.println("Class cast exception in AddProductToRequestStoredProc.addRequestProductToUpdatedRequest, check DB");
                throw new TypeMismatchDataAccessException(e.getMessage());
            }
        }
    }
}
