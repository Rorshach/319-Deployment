package com.coastcapitalsavings.datasource;


import java.io.*;

import com.google.gson.Gson;

/**
 * Responsbile for parsing out datasource connection strings from a datasource configuration file
 */
public class JSONDataSourceInfoParser implements IDataSourceInfoParser {

    /**
     * Creates a DB info object which has connection information parsed from
     * the supplied filename
     *
     * @param fileName full path to a file to parse for DB connection data
     */

    public DatasourceInfo createDatabaseInfo(String fileName) throws IOException {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, DatasourceInfo.class);
        }
    }
}
