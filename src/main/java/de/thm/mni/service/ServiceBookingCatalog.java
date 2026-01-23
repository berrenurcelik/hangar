package de.thm.mni.service;

import de.thm.mni.model.ServiceBooking;
import de.thm.mni.repository.ServiceBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC FB.8: ServiceBookingCatalog
 * Im Diagramm: "3.1: add(sb)"
 */
@Component
public class ServiceBookingCatalog {

    @Autowired
    private ServiceBookingRepository repository;

    /**
     * Im Diagramm: "3.1: add(sb)" Nachricht
     */
    public void add(ServiceBooking sb) {
        repository.save(sb);
    }
}
