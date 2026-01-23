package de.thm.mni.service;

import de.thm.mni.model.FlightReadiness;
import de.thm.mni.model.MaintenanceStatus;
import de.thm.mni.repository.FlightReadinessRepository;
import de.thm.mni.repository.MaintenanceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC HA.5: StatusCatalog
 * Im Diagramm: "5: add(mt, fr)"
 */
@Component
public class StatusCatalog {

    @Autowired
    private MaintenanceStatusRepository maintenanceStatusRepository;
    
    @Autowired
    private FlightReadinessRepository flightReadinessRepository;

    /**
     * Im Diagramm: "5: add(mt, fr)" Nachricht
     */
    public void add(MaintenanceStatus mt, FlightReadiness fr) {
        maintenanceStatusRepository.save(mt);
        flightReadinessRepository.save(fr);
    }
}
