package de.thm.mni.controller;

import de.thm.mni.model.ServiceRequest;
import de.thm.mni.service.SRequestManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/service-request")
@CrossOrigin(origins = "*")
public class ServiceRequestController {
    
    @Autowired
    private SRequestManagement sRequestManagement;
    
    /**
     * UC HA.6: saveRequest Endpunkt
     */
    @PostMapping("/save")
    public ResponseEntity<ServiceRequest> saveRequest(@RequestBody Map<String, Object> request) {
        try {
            Long acId = Long.valueOf(request.get("acId").toString());
            Long hpId = Long.valueOf(request.get("hpId").toString());
            Long mpId = Long.valueOf(request.get("mpId").toString());
            String services = request.get("services").toString();
            String location = request.get("location").toString();
            
            ServiceRequest sr = sRequestManagement.saveRequest(acId, hpId, mpId, services, location);
            return ResponseEntity.ok(sr);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * HA.6: Reparatur- / Wartungsanfrage durch Hangaranbieter (ID-basiert)
     */
    @PostMapping("/save-by-provider")
    public ResponseEntity<?> saveRequestByProvider(@RequestBody Map<String, Object> request) {
        List<String> missing = new ArrayList<>();
        Long hpId = null;
        Long aircraftId = null;
        Long mpId = null;
        if (request != null && request.get("hpId") != null) {
            try {
                hpId = request.get("hpId") instanceof Number
                    ? ((Number) request.get("hpId")).longValue()
                    : Long.parseLong(request.get("hpId").toString());
            } catch (NumberFormatException e) { /* leave null */ }
        }
        if (request != null && request.get("aircraftId") != null) {
            try {
                aircraftId = request.get("aircraftId") instanceof Number
                    ? ((Number) request.get("aircraftId")).longValue()
                    : Long.parseLong(request.get("aircraftId").toString());
            } catch (NumberFormatException e) { /* leave null */ }
        }
        if (request != null && request.get("maintenanceProviderId") != null) {
            try {
                mpId = request.get("maintenanceProviderId") instanceof Number
                    ? ((Number) request.get("maintenanceProviderId")).longValue()
                    : Long.parseLong(request.get("maintenanceProviderId").toString());
            } catch (NumberFormatException e) { /* leave null */ }
        }
        String services = request != null && request.get("services") != null ? request.get("services").toString().trim() : null;
        String location = request != null && request.get("location") != null ? request.get("location").toString().trim() : null;

        if (hpId == null) missing.add("hpId");
        if (aircraftId == null) missing.add("aircraftId");
        if (mpId == null) missing.add("maintenanceProviderId");
        if (services == null || services.isEmpty()) missing.add("services");
        if (location == null || location.isEmpty()) missing.add("location");

        if (!missing.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "VALIDATION");
            err.put("message", "Pflichtfelder fehlen oder ungültige Werte. Bitte fehlende oder fehlerhafte Daten korrigieren.");
            err.put("missingFields", missing);
            return ResponseEntity.badRequest().body(err);
        }

        try {
            ServiceRequest sr = sRequestManagement.saveRequest(aircraftId, hpId, mpId, services, location);
            return ResponseEntity.ok(sr);
        } catch (RuntimeException e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "SAVE_FAILED");
            err.put("message", e.getMessage() != null ? e.getMessage() : "Anfrage konnte nicht gespeichert werden.");
            return ResponseEntity.badRequest().body(err);
        }
    }
    
    /**
     * UC FB.4: saveRequest Endpunkt (AircraftOwner perspective)
     */
    @PostMapping("/save-aco")
    public ResponseEntity<ServiceRequest> saveRequestACO(@RequestBody Map<String, Object> request) {
        try {
            Long hpId = Long.valueOf(request.get("hpId").toString());
            Long acId = Long.valueOf(request.get("acId").toString());
            String duration = request.get("duration").toString();
            String services = request.get("services").toString();
            
            ServiceRequest sr = sRequestManagement.saveRequest(hpId, acId, duration, services);
            return ResponseEntity.ok(sr);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
