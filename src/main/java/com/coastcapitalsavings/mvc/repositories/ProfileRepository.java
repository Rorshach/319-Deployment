package com.coastcapitalsavings.mvc.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.coastcapitalsavings.mvc.models.Profile;
import com.coastcapitalsavings.mvc.repositories.mapper.ProfileGroupSummaryMapper;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO for profile associated operations
 */
@Repository
public class ProfileRepository {

    JdbcTemplate jdbcTemplate;
    GetAllProfilesSummaryStoredProc getAllProfilesSummaryStoredProc;
    GetProfileByIdStoredProc getProfileByIdStoredProc;
    CheckProfileExistsStoredProc checkProfileExistsStoredProc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        getAllProfilesSummaryStoredProc = new GetAllProfilesSummaryStoredProc();
        getProfileByIdStoredProc = new GetProfileByIdStoredProc();
        checkProfileExistsStoredProc = new CheckProfileExistsStoredProc();
    }

    /**
     * Gets a list of profiles, without the associated product info
     * @return List of Profiles
     */
    public List<Profile> getAllProfiles() {
        return getAllProfilesSummaryStoredProc.execute();
    }


    /**
     * Gets a single profile given it's id.  This method is not responsible for populating the
     * list of products associated with the profile.  For that, invoke the appropriate methods in the
     * products repository.
     * @param profileCode profileCode of the record to look up
     * @return profile record without products initialized
     */
    public Profile getProfileById(String profileCode) {
        return getProfileByIdStoredProc.execute(profileCode);
    }

    /**
     * Check if a profile exists given a potential profile id
     * @param profileCode profile code to check
     * @return true if exists, false otherwise
     */
    public boolean checkProfileExists(String profileCode) {
        return checkProfileExistsStoredProc.execute(profileCode);
    }




    /**
     * A stored procedure which checks if a profile exists
     */
    private class CheckProfileExistsStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_profileGroup_lookupExists";

        private CheckProfileExistsStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_profileCode", Types.VARCHAR));
            declareParameter(new SqlOutParameter("out_exists", Types.BOOLEAN));
            compile();
        }

        private boolean execute(String profileCode) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_profileCode", profileCode);

            Map<String, Object> outputs = execute(inputs);
            return (boolean) outputs.get("out_exists");
        }
    }


    /**
     * A stored procedure which retrieves a profile given its id.  The profile product information
     * is not retrieved
     */
    private class GetProfileByIdStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_profileGroup_lookupById";

        private GetProfileByIdStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlInOutParameter("inout_profileCode", Types.VARCHAR));
            declareParameter(new SqlOutParameter("out_profileDescription", Types.VARCHAR));
            compile();
        }

        private Profile execute(String profileCode) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_profileCode", profileCode);

            Map<String, Object> outputs = execute(inputs);
            return mapResponseToProfile(outputs);
        }

        private Profile mapResponseToProfile(Map<String, Object> responseMap) {
            Profile pro = new Profile();
            pro.setCode((String) responseMap.get("inout_profileCode"));
            pro.setDescription((String) responseMap.get("out_profileDescription"));
            return pro;
        }
    }


    /**
     * A stored procedure to retrieve a list of all profiles.  Each profile will have an id and a name,
     * but the list of products is not retrieved.
     */
    private class GetAllProfilesSummaryStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_profileGroup_lookupAll";

        private GetAllProfilesSummaryStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlReturnResultSet("profiles", new ProfileGroupSummaryMapper()));
        }

        private List<Profile> execute() {
            Map<String, Object> inputs = new HashMap<>();       // No input parameters to load
            Map<String, Object> outputs = execute(inputs);
            return (List<Profile>) outputs.get("profiles");
        }
    }
}
