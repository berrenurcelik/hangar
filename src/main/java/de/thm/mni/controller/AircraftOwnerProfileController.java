package de.thm.mni.controller;

import de.thm.mni.model.Aircraft;
import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.Benutzer;
import de.thm.mni.repository.AircraftOwnerRepository;
import de.thm.mni.repository.AircraftRepository;
import de.thm.mni.service.ACOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aircraft-owner")
@CrossOrigin(origins = "*")
public class AircraftOwnerProfileController {
    
    @Autowired
    private ACOService acoService;
    
    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;

    @Autowired
    private AircraftRepository aircraftRepository;
    
    /**
     * UC FB.1: saveProfile Endpunkt
     */
    @PostMapping("/save-profile")
    public ResponseEntity<AircraftOwner> saveProfile(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String contacts = request.get("contacts");
            String email = request.get("email");
            String password = request.get("password");
            String roleStr = request.getOrDefault("role", "AIRCRAFT_OWNER");
            
            Benutzer.Role role = Benutzer.Role.valueOf(roleStr);
            
            AircraftOwner aco = acoService.saveProfile(name, contacts, email, password, role);
            return ResponseEntity.ok(aco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Profil anzeigen - Flugzeugbesitzer Profil
     */
    @GetMapping("/profile/{id}")
    public ResponseEntity<AircraftOwner> getProfile(@PathVariable Long id) {
        return aircraftOwnerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Profil nach E-Mail abrufen
     */
    @GetMapping("/profile/by-email")
    public ResponseEntity<AircraftOwner> getProfileByEmail(@RequestParam String email) {
        return aircraftOwnerRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * UC FB.4: Flugzeuge des Flugzeugbesitzers (z. B. für Serviceanfrage-Dropdown)
     */
    @GetMapping("/aircrafts")
    public ResponseEntity<List<Aircraft>> getAircraftsByEmail(@RequestParam String email) {
        AircraftOwner owner = aircraftOwnerRepository.findByEmail(email)
                .orElse(null);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        List<Aircraft> aircrafts = aircraftRepository.findByAircraftOwner_Id(owner.getId());
        return ResponseEntity.ok(aircrafts);
    }

    /**
     * FB.9: Neues Flugzeug hinzufügen – Flugzeugbesitzer legt Flugzeuginformationen an.
     * Pflichtfelder: dimensions, registrationMark. Bei Verletzung 400 mit Fehlermeldung.
     */
    @PostMapping("/aircrafts")
    public ResponseEntity<?> createAircraftForOwner(
            @RequestParam String email,
            @RequestBody Map<String, Object> body) {
        String dimensions = body != null ? (String) body.get("dimensions") : null;
        String registrationMark = body != null ? (String) body.get("registrationMark") : null;
        List<String> missing = new ArrayList<>();
        if (dimensions == null || dimensions.isBlank()) missing.add("dimensions");
        if (registrationMark == null || registrationMark.isBlank()) missing.add("registrationMark");
        if (!missing.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "VALIDATION");
            err.put("message", "Pflichtfelder fehlen oder sind fehlerhaft. Bitte fehlende Werte korrigieren: Abmessungen, Kennzeichen.");
            err.put("missingFields", missing);
            return ResponseEntity.badRequest().body(err);
        }
        AircraftOwner owner = aircraftOwnerRepository.findByEmail(email).orElse(null);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        Aircraft ac = new Aircraft();
        ac.setAircraftOwner(owner);
        ac.setDimensions(dimensions.trim());
        ac.setRegistrationMark(registrationMark.trim());
        if (body.get("size") != null && body.get("size") instanceof Number) {
            ac.setSize(((Number) body.get("size")).intValue());
        }
        if (body.get("maintenanceStatus") != null) {
            ac.setMaintenanceStatus(String.valueOf(body.get("maintenanceStatus")).trim());
        }
        if (body.get("flightReadiness") != null) {
            ac.setFlightReadiness(String.valueOf(body.get("flightReadiness")).trim());
        }
        ac = aircraftRepository.save(ac);
        return ResponseEntity.ok(ac);
    }
}
