package de.thm.mni.controller;

import de.thm.mni.model.AircraftType;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.repository.HangarProviderRepository;
import de.thm.mni.service.ACTypeCatalog;
import de.thm.mni.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialization")
@CrossOrigin(origins = "*")
public class SpecializationController {
    
    @Autowired
    private SpecializationService specializationService;
    
    @Autowired
    private ACTypeCatalog acTypeCatalog;
    
    @Autowired
    private HangarProviderRepository hangarProviderRepository;
    
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
     * UC HA.4.2: addNewType. StR.L.4 4a/4b: strukturierte Fehlermeldung.
     */
    @PostMapping("/add-new-type")
    public ResponseEntity<?> addNewType(@RequestBody Map<String, String> request) {
        try {
            String name = request != null ? request.get("name") : null;
            String type = request != null ? request.get("type") : null;
            String size = request != null ? request.get("size") : null;
            AircraftType aircraftType = specializationService.addNewType(name, type, size);
            return ResponseEntity.ok(aircraftType);
        } catch (IllegalArgumentException e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage().contains("existiert") ? "DUPLICATE" : "INVALID_CHARS");
            err.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(err);
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
     * Alle verfügbaren Aircraft Types abrufen (id -> name)
     */
    @GetMapping("/aircraft-types")
    public ResponseEntity<Map<Long, String>> getAllAircraftTypes() {
        return ResponseEntity.ok(acTypeCatalog.getAllTypes());
    }

    /** HA.4: Alle Flugzeugtypen als Liste (id, name, type, size) für UI */
    @GetMapping("/aircraft-types-list")
    public ResponseEntity<List<AircraftType>> getAircraftTypesList() {
        return ResponseEntity.ok(acTypeCatalog.getAllTypesAsList());
    }

    /** StR.L.4: Aktuelle Auswahl des Hangaranbieters (selectedIds) */
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentSelection(@RequestParam String email) {
        HangarProvider hp = hangarProviderRepository.findByEmail(email).orElse(null);
        if (hp == null) return ResponseEntity.notFound().build();
        Map<Long, String> all = acTypeCatalog.getAllTypes();
        List<Long> selectedIds = new ArrayList<>();
        for (Map.Entry<Long, String> e : all.entrySet()) {
            if (hp.getAircraftTypes() != null && hp.getAircraftTypes().contains(e.getValue()))
                selectedIds.add(e.getKey());
        }
        return ResponseEntity.ok(Map.of("selectedIds", selectedIds));
    }

    /** HA.4: Spezialisierung bestätigen – Hangaranbieter per E-Mail. 6a: mindestens ein Typ. */
    @PostMapping("/confirm-by-provider")
    public ResponseEntity<?> confirmByProvider(@RequestBody Map<String, Object> request) {
        String email = request != null ? (String) request.get("email") : null;
        @SuppressWarnings("unchecked")
        List<Number> raw = request != null && request.get("aircraftTypeIds") instanceof List ? (List<Number>) request.get("aircraftTypeIds") : null;
        List<Long> aircraftTypeIds = raw != null ? raw.stream().map(Number::longValue).collect(Collectors.toList()) : List.of();
        if (email == null || email.isBlank()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "VALIDATION");
            err.put("message", "Bitte mindestens einen Flugzeugtyp auswählen.");
            err.put("missingFields", List.of("email"));
            return ResponseEntity.badRequest().body(err);
        }
        if (aircraftTypeIds == null || aircraftTypeIds.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "NO_SELECTION");
            err.put("message", "Bitte mindestens einen Flugzeugtyp auswählen.");
            return ResponseEntity.badRequest().body(err);
        }
        HangarProvider hp = hangarProviderRepository.findByEmail(email).orElse(null);
        if (hp == null) return ResponseEntity.notFound().build();
        try {
            HangarProvider updated = specializationService.setTypes(hp.getId(), aircraftTypeIds);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
