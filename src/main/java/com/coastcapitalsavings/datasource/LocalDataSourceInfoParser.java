package com.coastcapitalsavings.datasource;

import java.io.IOException;

/**
 * Created by Chris on 2017-02-19.
 */
public class LocalDataSourceInfoParser implements IDataSourceInfoParser {

    @Override
    public DatasourceInfo createDatabaseInfo(String fileName) throws IOException {
        return new DatasourceInfo("jdbc:mysql://127.0.0.1/cpsc319?useSSL=false", "root", "");
    }
}
