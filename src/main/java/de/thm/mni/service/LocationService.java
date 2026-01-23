package de.thm.mni.service;

import de.thm.mni.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UC FB.3: LocationService
 */
@Service
public class LocationService {

    @Autowired
    private LocationCatalog locationCatalog;

    /**
     * UC FB.3: searchLocationDetails
     * 1: found := searchLocationDetails(locationID) -> LocationCatalog
     * 2a [found = true]: loadLocationDetails(location, features, services, conditions)
     * 2b [found = false]: detailsNotAvailable()
     */
    public Location searchLocationDetails(Long locationID) {
        // 1: LocationCatalog durchsuchen
        Optional<Location> found = locationCatalog.searchLocationDetails(locationID);
        
        // 2a/2b: Prüfen ob gefunden
        if (found.isEmpty()) {
            // 2b: Details nicht verfügbar
            throw new RuntimeException("Location-Details nicht verfügbar für ID: " + locationID);
        }
        
        // 2a: Location-Details laden und zurückgeben
        return found.get();
    }
}
