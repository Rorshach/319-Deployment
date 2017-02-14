package com.coastcapitalsavings.database;

import java.io.IOException;


/**
 * Interface for implementing different database parsers
 */
public interface IDatabaseInfoParser {
    /**
     * Creates a database info object which has the properties needed to establish a connection
     * including url, schema, username, and password.
     * @param fileName full path to a file containing the database connection info
     * @return A database info object containing database properties
     * @throws IOException  usually because the file can't be found
     */
    public DatabaseInfo createDatabaseInfo(String fileName) throws IOException;
}
