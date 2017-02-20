package com.coastcapitalsavings.datasource;

import java.io.IOException;


/**
 * Interface for implementing different datasource parsers
 */
public interface IDataSourceInfoParser {
    /**
     * Creates a datasource info object which has the properties needed to establish a connection
     * including url, schema, username, and password.
     * @param fileName full path to a file containing the datasource connection info
     * @return A datasource info object containing datasource properties
     * @throws IOException  usually because the file can't be found
     */
    public DatasourceInfo createDatabaseInfo(String fileName) throws IOException;
}
