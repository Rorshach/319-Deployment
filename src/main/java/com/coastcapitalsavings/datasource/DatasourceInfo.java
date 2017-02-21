package com.coastcapitalsavings.datasource;

public class DatasourceInfo {
    private String address;
    private String userName;
    private String password;

    public DatasourceInfo (String address, String userName, String password) {
        this.address = address;
        this.userName = userName;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
