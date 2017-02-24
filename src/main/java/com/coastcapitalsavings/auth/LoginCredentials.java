package com.coastcapitalsavings.auth;

import lombok.Data;

/**
 * Created by Nancy on 2017-02-21.
 */
@Data
public class LoginCredentials {
    private String userEmail;
    private String password;
}
