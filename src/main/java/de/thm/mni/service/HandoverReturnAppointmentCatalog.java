package de.thm.mni.service;

import de.thm.mni.model.HandoverReturnAppointment;
import de.thm.mni.repository.HandoverReturnAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC FB.7: HandoverReturnAppointmentCatalog
 * Im Diagramm: "3.1: add(app)"
 */
@Component
public class HandoverReturnAppointmentCatalog {

    @Autowired
    private HandoverReturnAppointmentRepository repository;

    /**
     * Im Diagramm: "3.1: add(app)" Nachricht
     */
    public void add(HandoverReturnAppointment app) {
        repository.save(app);
    }
}
