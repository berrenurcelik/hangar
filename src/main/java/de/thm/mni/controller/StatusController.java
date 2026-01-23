package de.thm.mni.controller;

import de.thm.mni.service.HPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/status")
@CrossOrigin(origins = "*")
public class StatusController {
    
    @Autowired
    private HPService hpService;
    
    /**
     * UC HA.5: saveInput Endpunkt
     */
    @PostMapping("/save-input")
    public ResponseEntity<Void> saveInput(@RequestBody Map<String, Object> request) {
        try {
            Long hpId = Long.valueOf(request.get("hpId").toString());
            Long acId = Long.valueOf(request.get("acId").toString());
            String maintenance = request.get("maintenance").toString();
            String flightReadiness = request.get("flightReadiness").toString();
            
            hpService.saveInput(hpId, acId, maintenance, flightReadiness);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
