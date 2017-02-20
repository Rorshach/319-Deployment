package com.coastcapitalsavings.datasource;

import org.junit.Test;

import java.io.IOException;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class Test_JSONDatabaseInfoParser {

    @Test
    /**
     * Tests the JSON parser against a sample JSON file and confirms that the DataSourceInfo
     * object fields are as expected.
     */
    public void testDatabaseInfoCreation() {
        try {
            IDataSourceInfoParser parser = new JSONDataSourceInfoParser();
            DatasourceInfo dbInfo = parser.createDatabaseInfo("./src/test/resources/test_dbproperties.json");
            assertEquals("db.testAddress", dbInfo.getAddress());
            assertEquals("db.testUser", dbInfo.getUserName());
            assertEquals("db.testPassword", dbInfo.getPassword());
        } catch (IOException ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
