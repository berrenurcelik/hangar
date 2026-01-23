package de.thm.mni.service;

import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.Parking;
import de.thm.mni.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UC HA.3: ParkingService
 */
@Service
public class ParkingService {

    @Autowired
    private HangarCatalog hangarCatalog;
    
    @Autowired
    private ParkingCatalog parkingCatalog;
    
    @Autowired
    private ParkingRepository parkingRepository;
    
    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }
    
    @Transactional
    public Parking createParking(Parking parking) {
        return parkingRepository.save(parking);
    }
    
    @Transactional
    public void deleteParking(Long id) {
        parkingRepository.deleteById(id);
    }

    /**
     * UC HA.3: updateParking
     * 1: hangar := getHangar(hangarId) -> HangarCatalog
     * 2a: create(hangar, category, status) [number > 0] -> Parking
     * 2a.1: add(parking, number) -> ParkingCatalog
     * 2b: remove(hangar, category, status) [number < 0] -> Parking
     * 2b.1: remove(parking, number) -> ParkingCatalog
     */
    @Transactional
    public Parking updateParking(Long hangarId, String category, String status, Integer number) {
        // 1: Hangar aus HangarCatalog holen
        HangarProvider hangar = hangarCatalog.getHangar(hangarId);
        
        if (number > 0) {
            // 2a: Neuen Parking erstellen
            Parking parking = new Parking(status, 0, category);
            parking.setHangarProvider(hangar);
            
            // 2a.1: Zum ParkingCatalog hinzufügen
            parkingCatalog.add(parking, number);
            
            return parking;
        } else if (number < 0) {
            // 2b: Bestehenden Parking finden und entfernen
            Parking parking = findParking(hangar, category, status);
            if (parking == null) {
                throw new RuntimeException("Parking nicht gefunden für die angegebenen Kriterien");
            }
            
            // 2b.1: Aus ParkingCatalog entfernen
            parkingCatalog.remove(parking, number);
            
            return parking;
        } else {
            throw new IllegalArgumentException("Anzahl darf nicht 0 sein");
        }
    }
    
    /**
     * Hilfsmethode: Findet Parking nach Hangar, Kategorie und Status
     */
    private Parking findParking(HangarProvider hangar, String category, String status) {
        return hangar.getParkings().stream()
                .filter(p -> category.equals(p.getSiteStatus()) && status.equals(p.getStatus()))
                .findFirst()
                .orElse(null);
    }
}
