package de.thm.mni.service;

import de.thm.mni.model.Aircraft;
import de.thm.mni.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * UC HA.4.3 & FB.9: ACCatalog (AircraftCatalog)
 * Im Diagramm: "2: ac = get(aircraft)" (HA.4.3)
 * Im Diagramm: "1: found := get(aircraftID)" (FB.9)
 * Im Diagramm: "3a.1: add(aircraft)" (FB.9)
 * Im Diagramm: "3b.1: update(aircraft)" (FB.9)
 */
@Component
public class ACCatalog {

    @Autowired
    private AircraftRepository repository;

    /**
     * Im Diagramm: "2: ac = get(aircraft)" Nachricht (HA.4.3)
     * Exception werfen wenn nicht gefunden
     */
    public Aircraft get(Long aircraftId) {
        return repository.findById(aircraftId)
                .orElseThrow(() -> new RuntimeException("Aircraft nicht gefunden mit ID: " + aircraftId));
    }
    
    /**
     * UC FB.9: Im Diagramm "1: found := get(aircraftID)"
     * Optional zurückgeben für found=true/false Prüfung
     */
    public Optional<Aircraft> findById(Long aircraftId) {
        return repository.findById(aircraftId);
    }
    
    /**
     * UC FB.9: Im Diagramm "3a.1: add(aircraft)" (found=false)
     */
    public void add(Aircraft aircraft) {
        repository.save(aircraft);
    }
    
    /**
     * UC FB.9: Im Diagramm "3b.1: update(aircraft)" (found=true)
     */
    public void update(Aircraft aircraft) {
        repository.save(aircraft);
    }
}
