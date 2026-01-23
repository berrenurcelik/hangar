package de.thm.mni.controller;

import de.thm.mni.model.ServiceRequest;
import de.thm.mni.service.SRequestManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
