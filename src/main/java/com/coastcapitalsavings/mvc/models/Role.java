package com.coastcapitalsavings.mvc.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Employee roles
 */
@Data
public class Role {
    long id;
    String name;

    public Role (long role_id) {
        id = role_id;
        name = role_map.get(role_id);
    }

    private static final Map<Integer, String> role_map = new HashMap<Integer, String>() {{
        put(1, "Employee");
        put(2, "Approver");
        put(3, "IT Admin");
        put(4, "Approver/IT Admin");
    }};
}