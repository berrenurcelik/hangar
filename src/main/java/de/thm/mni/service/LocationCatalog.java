package de.thm.mni.service;

import de.thm.mni.model.Location;
import de.thm.mni.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * UC FB.3: LocationCatalog
 * Im Diagramm: "1: found := searchLocationDetails(locationID)"
 */
@Component
public class LocationCatalog {

    @Autowired
    private LocationRepository repository;

    /**
     * Im Diagramm: "1: found := searchLocationDetails(locationID)" Nachricht
     */
    public Optional<Location> searchLocationDetails(Long locationID) {
        return repository.findById(locationID);
    }
}
