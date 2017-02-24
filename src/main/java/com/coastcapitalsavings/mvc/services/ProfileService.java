package com.coastcapitalsavings.mvc.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.coastcapitalsavings.mvc.models.Product;
import com.coastcapitalsavings.mvc.models.Profile;
import com.coastcapitalsavings.mvc.repositories.ProductRepository;
import com.coastcapitalsavings.mvc.repositories.ProfileRepository;

import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;

/**
 * Business logic for the profiles controller
 */
@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepo;

    @Autowired
    ProductRepository productRepo;

    public List<Profile> getAllProfiles() {
        return profileRepo.getAllProfiles();
    }

    public Profile getProfilebyId(String profileCode) {
        if (profileRepo.checkProfileExists(profileCode)) {
            Profile profile = profileRepo.getProfileById(profileCode);
            List<Product> products = productRepo.getProductsInProfileByProfileCode(profileCode);
            profile.setProducts(products);
            return profile;
        } else {
            throw new DataRetrievalFailureException("Cannot find resource in database: Profile: id " + profileCode);
        }
    }
}
