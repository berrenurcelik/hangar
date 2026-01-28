package de.thm.mni.controller;

import de.thm.mni.model.Aircraft;
import de.thm.mni.service.HPService;
import de.thm.mni.service.StatusCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/status")
@CrossOrigin(origins = "*")
public class StatusController {

    @Autowired
    private HPService hpService;

    @Autowired
    private StatusCatalog statusCatalog;

    /**
     * HA.5: Liste der eingelagerten Flugzeuge (dem Hangaranbieterprofil zugeordnet)
     */
    @GetMapping("/aircraft-in-hangar")
    public ResponseEntity<List<Aircraft>> getAircraftInHangar(@RequestParam String email) {
        try {
            List<Aircraft> list = hpService.getAircraftInHangarByEmail(email);
            return ResponseEntity.ok(list != null ? list : new ArrayList<>());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * HA.5: aktuelle Wartungs- und Fahrbereitschaftsdaten für ein Flugzeug
     */
    @GetMapping("/current")
    public ResponseEntity<Map<String, String>> getCurrentStatus(@RequestParam Long aircraftId) {
        Map<String, String> data = statusCatalog.getCurrentForAircraft(aircraftId);
        return ResponseEntity.ok(data);
    }

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

    /**
     * HA.5: Wartungs- und Flugbereitschaftsdaten per E-Mail speichern (7a/7a1/7a2 Validierung)
     */
    @PostMapping("/save-input-by-provider")
    public ResponseEntity<?> saveInputByProvider(@RequestBody Map<String, Object> request) {
        List<String> missing = new ArrayList<>();
        String email = request != null ? (String) request.get("email") : null;
        Long aircraftId = null;
        if (request != null && request.get("aircraftId") != null) {
            try {
                aircraftId = request.get("aircraftId") instanceof Number
                    ? ((Number) request.get("aircraftId")).longValue()
                    : Long.parseLong(request.get("aircraftId").toString());
            } catch (NumberFormatException e) { /* leave null */ }
        }
        String maintenance = request != null && request.get("maintenance") != null ? request.get("maintenance").toString().trim() : null;
        String flightReadiness = request != null && request.get("flightReadiness") != null ? request.get("flightReadiness").toString().trim() : null;

        if (email == null || email.isBlank()) missing.add("email");
        if (aircraftId == null) missing.add("aircraftId");
        if (maintenance == null || maintenance.isEmpty()) missing.add("maintenance");
        if (flightReadiness == null || flightReadiness.isEmpty()) missing.add("flightReadiness");

        if (!missing.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "VALIDATION");
            err.put("message", "Pflichtfelder fehlen oder ungültige Werte. Bitte fehlende oder fehlerhafte Daten korrigieren.");
            err.put("missingFields", missing);
            return ResponseEntity.badRequest().body(err);
        }

        try {
            hpService.saveInputByProvider(email, aircraftId, maintenance, flightReadiness);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "SAVE_FAILED");
            err.put("message", e.getMessage() != null ? e.getMessage() : "Speichern fehlgeschlagen.");
            return ResponseEntity.badRequest().body(err);
        }
    }
}
