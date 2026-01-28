package de.thm.mni.service;

import de.thm.mni.model.Location;
import de.thm.mni.model.Parking;
import de.thm.mni.repository.LocationRepository;
import de.thm.mni.repository.ParkingRepository;
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
    
    @Autowired
    private ParkingRepository parkingRepository;

    /**
     * Im Diagramm: "1: found := searchLocationDetails(locationID)" Nachricht
     */
    public Optional<Location> searchLocationDetails(Long locationID) {
        return repository.findById(locationID);
    }
    
    /**
     * UC FB.3: Location über Parking ID abrufen
     * Flugzeugbesitzer wählt Standort aus Suchergebnisliste
     */
    public Location getLocationByParkingId(Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("Parking nicht gefunden"));
        if (parking.getLocation() == null) {
            throw new RuntimeException("Details momentan nicht verfügbar");
        }
        
        return parking.getLocation();
    }

    /**
     * Liefert eine "Standard"-Location für eine Stadt.
     * Wird z.B. genutzt, um neu angelegte Stellplätze eines Hangaranbieters
     * automatisch mit konsistenten Standortdetails zu verknüpfen.
     */
    public Location getDefaultLocationForCity(String city) {
        if (city == null || city.isBlank()) {
            throw new RuntimeException("Stadt für Standortdetails ist nicht gesetzt");
        }
        return repository.findFirstByLocationContainingIgnoreCase(city)
                .orElseThrow(() -> new RuntimeException("Keine Standortdetails für Stadt " + city + " gefunden"));
    }
}
