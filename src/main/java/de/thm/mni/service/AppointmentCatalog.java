package de.thm.mni.service;

import de.thm.mni.model.Appointment;
import de.thm.mni.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC HA.7: AppointmentCatalog
 * Im Diagramm: "4.1: add(app)"
 */
@Component
public class AppointmentCatalog {

    @Autowired
    private AppointmentRepository repository;

    /**
     * Im Diagramm: "4.1: add(app)" Nachricht
     */
    public void add(Appointment app) {
        repository.save(app);
    }
}
