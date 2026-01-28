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
            // 2b: Details nicht verfügbar (UC FB.3: 2a1)
            throw new RuntimeException("Details momentan nicht verfügbar");
        }
        
        // 2a: Location-Details laden und zurückgeben
        return found.get();
    }
    
    /**
     * UC FB.3: Location-Details über Parking ID abrufen
     * Flugzeugbesitzer wählt einen Standort aus der Suchergebnisliste (UC.FB.2)
     */
    public Location getLocationByParkingId(Long parkingId) {
        return locationCatalog.getLocationByParkingId(parkingId);
    }
}
