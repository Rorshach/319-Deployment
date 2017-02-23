package com.coastcapitalsavings.mvc.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.coastcapitalsavings.mvc.models.Profile;
import com.coastcapitalsavings.mvc.repositories.mapper.ProfileSummaryMapper;

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
     * list of products associated with the profile.  For that, use the products repository.
     * @return Profile with null list of products.
     */
    public Profile getProfileById(long profileId) {
        return getProfileByIdStoredProc.execute(profileId);
    }

    /**
     * Check if a profile exists given a potential profile id
     * @param profileId profile id to check
     * @return true if exists, false otherwise
     */
    public boolean checkProfileExists(long profileId) {
        return checkProfileExistsStoredProc.execute(profileId);
    }


    /**
     * A stored procedure which checks if a profile exists
     */
    private class CheckProfileExistsStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_profile_lookupExists";

        private CheckProfileExistsStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlParameter("in_id", Types.INTEGER));
            declareParameter(new SqlOutParameter("out_exists", Types.BOOLEAN));
            compile();
        }

        private boolean execute(long profileId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("in_id", profileId);

            Map<String, Object> outputs = execute(inputs);
            return (boolean) outputs.get("out_exists");
        }
    }


    /**
     * A stored procedure which retrieves a profile given its id.  The profile product information
     * is not retrieved
     */
    private class GetProfileByIdStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_profile_lookupById";

        private GetProfileByIdStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlInOutParameter("inout_id", Types.INTEGER));
            declareParameter(new SqlOutParameter("out_name", Types.VARCHAR));
            compile();
        }

        private Profile execute(long profileId) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("inout_id", profileId);

            Map<String, Object> outputs = execute(inputs);
            return mapResponseToProfile(outputs);
        }

        private Profile mapResponseToProfile(Map<String, Object> responseMap) {
            Profile pro = new Profile();
            pro.setId((long) responseMap.get("inout_id"));
            pro.setName((String) responseMap.get("out_name"));
            return pro;
        }
    }


    /**
     * A stored procedure to retrieve a list of all profiles.  Each profile will have an id and a name,
     * but the list of products is not retrieved.
     */
    private class GetAllProfilesSummaryStoredProc extends StoredProcedure {
        private static final String PROC_NAME = "req_profile_lookupAll";

        private GetAllProfilesSummaryStoredProc() {
            super(jdbcTemplate, PROC_NAME);
            declareParameter(new SqlReturnResultSet("profiles", new ProfileSummaryMapper()));
        }

        private List<Profile> execute() {
            Map<String, Object> inputs = new HashMap<>();       // No input parameters to load
            Map<String, Object> outputs = execute(inputs);
            return (List<Profile>) outputs.get("profiles");
        }
    }
}
