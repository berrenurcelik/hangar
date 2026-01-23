package de.thm.mni.service;

import de.thm.mni.model.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UC FB.2: SearchParking
 */
@Service
public class SearchParking {

    @Autowired
    private ParkingCatalog parkingCatalog;

    /**
     * UC FB.2: searchAvailableParking
     * 1: found := searchAvailableParking(city) -> ParkingCatalog
     * 2a [found = true]: results(parkings)
     * 2b [found = false]: noParkingFound()
     */
    public List<Parking> searchAvailableParking(String city) {
        // 1: ParkingCatalog durchsuchen
        List<Parking> parkings = parkingCatalog.searchAvailableParking(city);
        
        // 2a/2b: Prüfen ob etwas gefunden wurde
        if (parkings.isEmpty()) {
            // 2b: Keine Parkplätze gefunden
            throw new RuntimeException("Keine verfügbaren Parkplätze in " + city + " gefunden");
        }
        
        // 2a: Parkplätze zurückgeben
        return parkings;
    }
}
