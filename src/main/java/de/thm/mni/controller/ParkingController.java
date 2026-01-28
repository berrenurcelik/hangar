package de.thm.mni.controller;

import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.Parking;
import de.thm.mni.repository.HangarProviderRepository;
import de.thm.mni.repository.ParkingRepository;
import de.thm.mni.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parkings")
@CrossOrigin(origins = "*")
public class ParkingController {
    
    @Autowired
    private ParkingService parkingService;
    
    @Autowired
    private HangarProviderRepository hangarProviderRepository;
    
    @Autowired
    private ParkingRepository parkingRepository;
    
    @GetMapping
    public ResponseEntity<List<Parking>> getAllParkings() {
        try {
            List<Parking> parkings = parkingService.getAllParkings();
            return ResponseEntity.ok(parkings);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /** StR.L.3 Schritt 2: Liste der Stellplätze des Hangaranbieters */
    @GetMapping("/by-provider")
    public ResponseEntity<List<Parking>> getParkingsByProviderEmail(@RequestParam String email) {
        HangarProvider hp = hangarProviderRepository.findByEmail(email).orElse(null);
        if (hp == null) return ResponseEntity.notFound().build();
        List<Parking> list = parkingRepository.findByHangarProvider_Id(hp.getId());
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Parking> getParkingById(@PathVariable Long id) {
        return parkingService.getParkingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    
    /**
     * HA.3: Stellplätze bearbeiten – Hangaranbieter per E-Mail, 6a/6a1/6a2 Validierung.
     */
    @PostMapping("/update-by-provider")
    public ResponseEntity<?> updateParkingByProvider(@RequestBody Map<String, Object> request) {
        List<String> missing = new ArrayList<>();
        String email = request != null ? (String) request.get("email") : null;
        String category = request != null ? (String) request.get("category") : null;
        String status = request != null ? (String) request.get("status") : null;
        Integer number = null;
        if (request != null && request.get("number") != null) {
            try {
                number = request.get("number") instanceof Number
                    ? ((Number) request.get("number")).intValue()
                    : Integer.parseInt(request.get("number").toString());
            } catch (NumberFormatException e) { /* leave null */ }
        }
        if (email == null || email.isBlank()) missing.add("email");
        if (category == null || category.isBlank()) missing.add("category");
        if (status == null || status.isBlank()) missing.add("status");
        if (number == null || number == 0) missing.add("number");
        if (!missing.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "VALIDATION");
            err.put("message", "Pflichtfelder fehlen oder ungültige Werte. Bitte fehlende oder fehlerhafte Daten korrigieren.");
            err.put("missingFields", missing);
            return ResponseEntity.badRequest().body(err);
        }
        HangarProvider hp = hangarProviderRepository.findByEmail(email).orElse(null);
        if (hp == null) return ResponseEntity.notFound().build();
        try {
            Parking parking = parkingService.updateParking(hp.getId(), category.trim(), status.trim(), number);
            return ResponseEntity.ok(parking);
        } catch (RuntimeException e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "UPDATE_FAILED");
            err.put("message", e.getMessage() != null ? e.getMessage() : "Aktualisierung fehlgeschlagen.");
            return ResponseEntity.badRequest().body(err);
        }
    }
}
