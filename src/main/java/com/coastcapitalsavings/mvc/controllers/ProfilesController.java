package com.coastcapitalsavings.mvc.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coastcapitalsavings.mvc.models.Profile;
import com.coastcapitalsavings.mvc.models.modelviews.ModelViews;
import com.coastcapitalsavings.mvc.services.ProfileService;
import com.coastcapitalsavings.util.Responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;

/**
 * Controller for /profiles endpoint
 */
@RestController
@RequestMapping(value="/profiles")
public class ProfilesController {

    @Autowired
    ProfileService profileService;

    /**
     * Handles GET to /profiles
     * @return List of profiles without product info
     */
    @JsonView(ModelViews.Summary.class)
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profileSummary = profileService.getAllProfiles();
        return new ResponseEntity(profileSummary, HttpStatus.OK);
    }

    /**
     * Handles GET to /profiles/id
     * @return a Profile including all product info
     */
    @RequestMapping(value="/{profileId}", method=RequestMethod.GET)
    public ResponseEntity<Profile> getProfileById(@PathVariable long profileId) {
        try {
            Profile profile = profileService.getProfilebyId(profileId);
            return new ResponseEntity(profile, HttpStatus.OK);
        } catch (DataRetrievalFailureException e) {
            return new ResponseEntity(Responses.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
