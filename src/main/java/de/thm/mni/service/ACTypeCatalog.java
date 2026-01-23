package de.thm.mni.service;

import de.thm.mni.model.AircraftType;
import de.thm.mni.repository.AircraftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * UC HA.4.1 & HA.4.2: ACTypeCatalog
 * Im Diagramm: "2: acType = get(aircraftTypeId)" (HA.4.1)
 * Im Diagramm: "1.1: add(aircraftType)" (HA.4.2)
 */
@Component
public class ACTypeCatalog {

    @Autowired
    private AircraftTypeRepository repository;
    
    public ACTypeCatalog() {
    }

    /**
     * Im Diagramm: "2: acType = get(aircraftTypeId)" Nachricht (HA.4.1)
     */
    public String get(Long aircraftTypeId) {
        AircraftType aircraftType = repository.findById(aircraftTypeId)
                .orElseThrow(() -> new RuntimeException("Aircraft Type nicht gefunden mit ID: " + aircraftTypeId));
        return aircraftType.getName();
    }
    
    /**
     * Im Diagramm: "1.1: add(aircraftType)" Nachricht (HA.4.2)
     */
    public void add(AircraftType aircraftType) {
        repository.save(aircraftType);
    }
    
    public Map<Long, String> getAllTypes() {
        Map<Long, String> types = new HashMap<>();
        repository.findAll().forEach(at -> types.put(at.getId(), at.getName()));
        return types;
    }
}
