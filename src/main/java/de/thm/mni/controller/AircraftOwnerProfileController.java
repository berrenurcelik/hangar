package de.thm.mni.controller;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.Benutzer;
import de.thm.mni.service.ACOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/aircraft-owner")
@CrossOrigin(origins = "*")
public class AircraftOwnerProfileController {
    
    @Autowired
    private ACOService acoService;
    
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
}
