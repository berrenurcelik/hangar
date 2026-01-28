package de.thm.mni.service;

import de.thm.mni.model.ServiceBooking;
import de.thm.mni.repository.ServiceBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UC FB.8 : ServiceBookingCatalog
 */
@Component
public class ServiceBookingCatalog {

    @Autowired
    private ServiceBookingRepository repository;

    public void add(ServiceBooking sb) {
        repository.save(sb);
    }

    /** FB.8 Schritt 8: Buchungsübersicht des Flugzeugbesitzers */
    public List<ServiceBooking> findByAircraftOwnerId(Long aircraftOwnerId) {
        return repository.findByAircraftOwner_Id(aircraftOwnerId);
    }
}
