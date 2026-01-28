package de.thm.mni.controller;

import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.Service;
import de.thm.mni.repository.HangarProviderRepository;
import de.thm.mni.repository.ServiceRepository;
import de.thm.mni.service.HPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private HangarProviderRepository hangarProviderRepository;
    
    @Autowired
    private HPService hpService;
    
    /** FB.8 Schritt 2: Liste der angebotenen Zusatzservices (2a: leer → „Derzeit keine…“) */
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        try {
            List<Service> services = serviceRepository.findAll();
            return ResponseEntity.ok(services);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** FB.8 Schritt 2: Liste der Einlagerungsservices des Hangaranbieters (by-provider vor /{id}) */
    @GetMapping("/by-provider")
    public ResponseEntity<List<Service>> getServicesByProviderEmail(@RequestParam String email) {
        HangarProvider hp = hangarProviderRepository.findByEmail(email).orElse(null);
        if (hp == null) return ResponseEntity.notFound().build();
        List<Service> list = serviceRepository.findByHangarProvider_Id(hp.getId());
        return ResponseEntity.ok(list);
    }
    
    /** FB.8 Schritt 4: Details eines Services */
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /** HA.2: Neuen Service anlegen. 6a/6a1/6a2: Pflichtfelder name, description, cost. */
    @PostMapping("/by-provider")
    public ResponseEntity<?> createServiceByProvider(@RequestBody Map<String, Object> body) {
        List<String> missing = new ArrayList<>();
        String name = body != null ? (String) body.get("name") : null;
        String description = body != null ? (String) body.get("description") : null;
        Object costObj = body != null ? body.get("cost") : null;
        Integer cost = null;
        if (costObj instanceof Number) cost = ((Number) costObj).intValue();
        else if (costObj != null && !costObj.toString().isBlank()) {
            try { cost = Integer.parseInt(costObj.toString()); } catch (NumberFormatException e) { cost = null; }
        }
        if (name == null || name.isBlank()) missing.add("name");
        if (description == null || description.isBlank()) missing.add("description");
        if (cost == null || cost < 0) missing.add("cost");
        String email = body != null ? (String) body.get("email") : null;
        if (email == null || email.isBlank()) missing.add("email");
        if (!missing.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "VALIDATION");
            err.put("message", "Pflichtfelder fehlen oder ungültige Werte. Bitte fehlende oder fehlerhafte Daten korrigieren.");
            err.put("missingFields", missing);
            return ResponseEntity.badRequest().body(err);
        }
        HangarProvider hp = hangarProviderRepository.findByEmail(email).orElse(null);
        if (hp == null) return ResponseEntity.notFound().build();
        @SuppressWarnings("unchecked")
        Set<String> conditions = body.get("conditions") != null && body.get("conditions") instanceof List
            ? ((List<?>) body.get("conditions")).stream().map(String::valueOf).filter(s -> s != null && !s.isBlank()).collect(Collectors.toSet())
            : Set.of();
        try {
            Service created = hpService.saveService(hp.getId(), name.trim(), description.trim(), cost, conditions);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        try {
            Service created = serviceRepository.save(service);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        try {
            serviceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
