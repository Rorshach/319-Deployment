package com.coastcapitalsavings.database;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class Test_JSONDatabaseInfoParser {

    @Test
    public void testDatabaseInfoCreation() {
        try {
            IDatabaseInfoParser parser = new JSONDatabaseInfoParser();
            DatabaseInfo dbInfo = parser.createDatabaseInfo("./src/test/resources/test_dbproperties.json");
            assertEquals("db.testAddress", dbInfo.getAddress());
            assertEquals("db.testUser", dbInfo.getUserName());
            assertEquals("db.testPassword", dbInfo.getPassword());
        } catch (IOException ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
