package com.coastcapitalsavings.database;


import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Responsbile for parsing out database connection strings from a database configuration file
 */
public class JSONDatabaseInfoParser implements IDatabaseInfoParser {

    /**
     * Creates a DB info object which has connection information parsed from
     * the supplied filename
     *
     * @param fileName full path to a file to parse for DB connection data
     */

    public DatabaseInfo createDatabaseInfo(String fileName) throws IOException {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, DatabaseInfo.class);
        }
    }
}
