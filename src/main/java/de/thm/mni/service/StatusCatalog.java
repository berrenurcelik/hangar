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

    /** HA.5: aktuelle Wartungs- und Flugbereitschaftsdaten für ein Flugzeug */
    public java.util.Map<String, String> getCurrentForAircraft(Long aircraftId) {
        java.util.Map<String, String> out = new java.util.HashMap<>();
        maintenanceStatusRepository.findTopByAircraft_IdOrderByIdDesc(aircraftId)
                .ifPresent(ms -> out.put("maintenance", ms.getMaintenance()));
        flightReadinessRepository.findTopByAircraft_IdOrderByIdDesc(aircraftId)
                .ifPresent(fr -> out.put("flightReadiness", fr.getFlightReadiness()));
        return out;
    }
}
