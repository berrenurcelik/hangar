package de.thm.mni.controller;

import de.thm.mni.model.Parking;
import de.thm.mni.service.SearchParking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchParkingController {
    
    @Autowired
    private SearchParking searchParking;
    
    /**
     * UC FB.2: searchAvailableParking Endpunkt
     */
    @GetMapping("/available-parking")
    public ResponseEntity<List<Parking>> searchAvailableParking(@RequestParam String city) {
        try {
            List<Parking> parkings = searchParking.searchAvailableParking(city);
            return ResponseEntity.ok(parkings);
        } catch (RuntimeException e) {
            // Keine Parkplätze gefunden
            return ResponseEntity.notFound().build();
        }
    }
}
