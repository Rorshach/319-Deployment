package com.coastcapitalsavings.mvc.repositories.mapper;

import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.models.RequestProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Constructs RequestProducts
 */
public class RequestProductMapper implements RowMapper<RequestProduct> {
    @Override
    public RequestProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));

        RequestProduct rp = new RequestProduct();
        rp.setProduct(p);
        rp.setProductStatus_id(rs.getLong("product_status_id"));

        return rp;
    }
}
