package com.coastcapitalsavings.auth;

import com.coastcapitalsavings.mvc.models.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Nancy on 2017-02-22.
 */
public class JwtAuthenticatedEmployee implements Authentication {

    private final Employee minimalProfile;

    public JwtAuthenticatedEmployee(Employee minimalProfile) {
        this.minimalProfile = minimalProfile;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
