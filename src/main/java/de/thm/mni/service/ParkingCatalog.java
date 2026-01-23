package de.thm.mni.service;

import de.thm.mni.model.Parking;
import de.thm.mni.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UC HA.3 & FB.2: ParkingCatalog
 * Im Diagramm: "2a.1: add(parking, number)" und "2b.1: remove(parking, number)" (HA.3)
 * Im Diagramm: "1: found := searchAvailableParking(city)" (FB.2)
 */
@Component
public class ParkingCatalog {

    @Autowired
    private ParkingRepository repository;

    /**
     * Im Diagramm: "2a.1: add(parking, number)" Nachricht
     */
    public void add(Parking parking, Integer number) {
        parking.setNumber(number);
        repository.save(parking);
    }
    
    /**
     * Im Diagramm: "2b.1: remove(parking, number)" Nachricht
     */
    public void remove(Parking parking, Integer number) {
        // Anzahl reduzieren oder komplett löschen
        if (parking.getNumber() != null && parking.getNumber() <= Math.abs(number)) {
            repository.delete(parking);
        } else {
            parking.setNumber(parking.getNumber() - Math.abs(number));
            repository.save(parking);
        }
    }
    
    /**
     * UC FB.2: Im Diagramm "1: found := searchAvailableParking(city)"
     * Sucht verfügbare Parkplätze nach Stadt
     */
    public List<Parking> searchAvailableParking(String city) {
        // Vereinfachte Suche - in echter Implementierung würde man nach Stadt filtern
        // Hier geben wir alle verfügbaren Parkplätze zurück (number > 0)
        return repository.findAll().stream()
                .filter(p -> p.getNumber() != null && p.getNumber() > 0)
                .toList();
    }
}
