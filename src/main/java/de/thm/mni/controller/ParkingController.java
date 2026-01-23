package de.thm.mni.controller;

import de.thm.mni.model.Parking;
import de.thm.mni.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parkings")
@CrossOrigin(origins = "*")
public class ParkingController {
    
    @Autowired
    private ParkingService parkingService;
    
    @GetMapping
    public ResponseEntity<List<Parking>> getAllParkings() {
        try {
            List<Parking> parkings = parkingService.getAllParkings();
            return ResponseEntity.ok(parkings);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Parking> createParking(@RequestBody Parking parking) {
        try {
            Parking created = parkingService.createParking(parking);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParking(@PathVariable Long id) {
        try {
            parkingService.deleteParking(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * UC HA.3: updateParking Endpunkt
     */
    @PostMapping("/update")
    public ResponseEntity<Parking> updateParking(@RequestBody Map<String, Object> request) {
        try {
            Long hangarId = Long.valueOf(request.get("hangarId").toString());
            String category = request.get("category").toString();
            String status = request.get("status").toString();
            Integer number = Integer.valueOf(request.get("number").toString());
            
            Parking parking = parkingService.updateParking(hangarId, category, status, number);
            return ResponseEntity.ok(parking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
