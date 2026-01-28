package de.thm.mni.service;

import de.thm.mni.model.Parking;
import de.thm.mni.repository.HangarProviderRepository;
import de.thm.mni.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UC HA.3 & FB.2: ParkingCatalog
 * Im Diagramm: "2a.1: add(parking, number)" und "2b.1: remove(parking, number)" (HA.3)
 * Im Diagramm: "1: found := searchAvailableParking(city)" (FB.2)
 */
@Component
public class ParkingCatalog {

    @Autowired
    private ParkingRepository repository;
    
    @Autowired
    private HangarProviderRepository hangarProviderRepository;

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
        // UC FB.2: Parkplätze nach Stadt filtern
        // Nur verfügbare Parkplätze (status = AVAILABLE) in der gewählten Stadt
        return repository.findAll().stream()
                .filter(p -> {
                    // Verfügbar sein (AVAILABLE status)
                    boolean isAvailable = "AVAILABLE".equalsIgnoreCase(p.getStatus());
                    // Stadt muss übereinstimmen (über HangarProvider)
                    boolean cityMatches = p.getHangarProvider() != null 
                            && p.getHangarProvider().getCity() != null
                            && p.getHangarProvider().getCity().equalsIgnoreCase(city);
                    return isAvailable && cityMatches;
                })
                .toList();
    }
    
    /**
     * UC FB.2: Liste der verfügbaren Großstädte
     * Gibt alle Städte zurück, in denen HangarProvider mit Parkplätzen existieren
     */
    public List<String> getAvailableCities() {
        return hangarProviderRepository.findAll().stream()
                .filter(hp -> hp.getCity() != null && !hp.getCity().trim().isEmpty())
                .map(hp -> hp.getCity())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
