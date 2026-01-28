package de.thm.mni.controller;

import de.thm.mni.dto.ErrorMessage;
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
    public ResponseEntity<?> searchLocationDetails(@PathVariable Long locationID) {
        try {
            Location location = locationService.searchLocationDetails(locationID);
            return ResponseEntity.ok(location);
        } catch (RuntimeException e) {
            // UC FB.3: 2a1 - Details nicht verfügbar
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }
    
    /**
     * UC FB.3: Location-Details über Parking ID
     * Flugzeugbesitzer wählt Standort aus Suchergebnisliste (UC.FB.2)
     */
    @GetMapping("/by-parking/{parkingId}")
    public ResponseEntity<?> getLocationByParking(@PathVariable Long parkingId) {
        try {
            Location location = locationService.getLocationByParkingId(parkingId);
            return ResponseEntity.ok(location);
        } catch (RuntimeException e) {
            // UC FB.3: 2a1 - Details nicht verfügbar
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }
}
