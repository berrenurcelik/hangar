package de.thm.mni.controller;

import de.thm.mni.model.AircraftType;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.service.ACTypeCatalog;
import de.thm.mni.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/specialization")
@CrossOrigin(origins = "*")
public class SpecializationController {
    
    @Autowired
    private SpecializationService specializationService;
    
    @Autowired
    private ACTypeCatalog acTypeCatalog;
    
    /**
     * UC HA.4.1: confirmTypes Endpunkt
     */
    @PostMapping("/confirm-types")
    public ResponseEntity<HangarProvider> confirmTypes(@RequestBody Map<String, Object> request) {
        try {
            Long hpId = Long.valueOf(request.get("hpId").toString());
            @SuppressWarnings("unchecked")
            List<Long> aircraftTypeIds = (List<Long>) request.get("aircraftTypeIds");
            
            HangarProvider hp = specializationService.confirmTypes(hpId, aircraftTypeIds);
            return ResponseEntity.ok(hp);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * UC HA.4.2: addNewType Endpunkt
     */
    @PostMapping("/add-new-type")
    public ResponseEntity<AircraftType> addNewType(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String type = request.get("type");
            String size = request.get("size");
            
            AircraftType aircraftType = specializationService.addNewType(name, type, size);
            return ResponseEntity.ok(aircraftType);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * UC HA.4.3: saveSelection Endpunkt
     */
    @PostMapping("/save-selection")
    public ResponseEntity<HangarProvider> saveSelection(@RequestBody Map<String, Object> request) {
        try {
            Long hpId = Long.valueOf(request.get("hpId").toString());
            @SuppressWarnings("unchecked")
            List<Long> selectedAircraftIds = (List<Long>) request.get("selectedAircraftIds");
            
            HangarProvider hp = specializationService.saveSelection(hpId, selectedAircraftIds);
            return ResponseEntity.ok(hp);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Alle verfügbaren Aircraft Types abrufen
     */
    @GetMapping("/aircraft-types")
    public ResponseEntity<Map<Long, String>> getAllAircraftTypes() {
        return ResponseEntity.ok(acTypeCatalog.getAllTypes());
    }
}
