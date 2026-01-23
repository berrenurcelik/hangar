package de.thm.mni.service;

import de.thm.mni.model.Aircraft;
import de.thm.mni.model.AircraftOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UC FB.9: AircraftService
 */
@Service
public class AircraftService {

    @Autowired
    private ACCatalog acCatalog;
    
    @Autowired
    private ACOCatalog acoCatalog;

    /**
     * UC FB.9: saveAircraft
     * 
     * Im Diagramm:
     * 1: found := get(aircraftID) -> AircraftCatalog
     * 2: aco := getACO(ACOId) -> ACOCatalog
     * 
     * [found = false]:
     *   3a: create(aircraftID, dimensions, registrationMark, image, aco) -> aircraft:Aircraft
     *   3a.1: add(aircraft) -> AircraftCatalog
     * 
     * [found = true]:
     *   3b: update(dimensions, registrationMark, image) -> aircraft:Aircraft
     *   3b.1: update(aircraft) -> AircraftCatalog
     */
    @Transactional
    public Aircraft saveAircraft(Long aircraftId, Long acoId, String dimensions, 
                                String registrationMark, byte[] image) {
        
        // 1: Aircraft aus AircraftCatalog holen
        Optional<Aircraft> foundAircraft = acCatalog.findById(aircraftId);
        
        // 2: AircraftOwner aus ACOCatalog holen
        AircraftOwner aco = acoCatalog.getACO(acoId);
        
        Aircraft aircraft;
        
        if (foundAircraft.isEmpty()) {
            // 3a: [found = false] Aircraft erstellen
            aircraft = new Aircraft(dimensions, registrationMark, image, aco);
            
            // 3a.1: Zum AircraftCatalog hinzufügen
            acCatalog.add(aircraft);
            
        } else {
            // 3b: [found = true] Aircraft aktualisieren
            aircraft = foundAircraft.get();
            
            // 3b: update aufrufen
            aircraft.update(dimensions, registrationMark, image);
            
            // 3b.1: Im AircraftCatalog aktualisieren
            acCatalog.update(aircraft);
        }
        
        return aircraft;
    }
}
