package com.coastcapitalsavings.unitTests;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.coastcapitalsavings.model.testDat;
import com.coastcapitalsavings.model.testImpl;

public class unitTestsDat {

    private EmbeddedDatabase db;

    testDat test;
    
    @Before
    public void setUp() {
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.HSQL)
    		.addScript("db/sql/schema.sql")
    		.addScript("db/sql/insert.sql")
    		.build();
    }

    @Test
    public void testGetName() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	testImpl info = new testImpl();
    	info.setNamedParameterJdbcTemplate(template);
    	testDat td = info.getName("snail");
    	Assert.assertNotNull(td);
    	System.out.println(td);
    }

    @Test
    public void testGetAll() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	testImpl test = new testImpl();
    	test.setNamedParameterJdbcTemplate(template);
    	List<testDat> info = test.getAll();
    	System.out.println(info);
    }
    
    @After
    public void tearDown() {
        db.shutdown();
    }

}