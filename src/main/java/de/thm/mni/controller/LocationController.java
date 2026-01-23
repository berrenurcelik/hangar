package de.thm.mni.controller;

import de.thm.mni.model.Location;
import de.thm.mni.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "*")
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    /**
     * UC FB.3: searchLocationDetails Endpunkt
     */
    @GetMapping("/details/{locationID}")
    public ResponseEntity<Location> searchLocationDetails(@PathVariable Long locationID) {
        try {
            Location location = locationService.searchLocationDetails(locationID);
            return ResponseEntity.ok(location);
        } catch (RuntimeException e) {
            // Details nicht verfügbar
            return ResponseEntity.notFound().build();
        }
    }
}
