package com.coastcapitalsavings.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coastcapitalsavings.model.testDat;

@Repository
public class testImpl implements tdat {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public testDat getName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
		String sql = "SELECT * FROM categories WHERE name=:name";
        testDat res = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    new testDatMapper());
        return res;
        
	}

	@Override
	public List<testDat> getAll() {
		Map<String, Object> params = new HashMap<String, Object>();		
		String sql = "SELECT * FROM categories";	
        List<testDat> res = namedParameterJdbcTemplate.query(sql, params, new testDatMapper());        
        return res;
	}

	private static final class testDatMapper implements RowMapper<testDat> {

		public testDat mapRow(ResultSet rs, int rowNum) throws SQLException {
			testDat info = new testDat();
			info.setId(rs.getInt("id"));
			info.setName(rs.getString("name"));
			return info;
		}
	}

}