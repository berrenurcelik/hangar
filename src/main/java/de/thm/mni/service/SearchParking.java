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
            // 2b: Keine Parkplätze gefunden (UC FB.2: 3a1, 3a2)
            throw new RuntimeException("Keine passenden Stellplätze verfügbar");
        }
        
        // 2a: Parkplätze zurückgeben
        return parkings;
    }
    
    /**
     * UC FB.2: Liste der verfügbaren Großstädte
     * Gibt alle Städte zurück, in denen HangarProvider existieren
     */
    public List<String> getAvailableCities() {
        return parkingCatalog.getAvailableCities();
    }
}
