package de.thm.mni.service;

import de.thm.mni.model.Aircraft;
import de.thm.mni.model.AircraftType;
import de.thm.mni.model.HangarProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UC HA.4.1, HA.4.2, HA.4.3: SpecializationService
 */
@Service
public class SpecializationService {

    @Autowired
    private HPCatalog hpCatalog;
    
    @Autowired
    private ACTypeCatalog acTypeCatalog;
    
    @Autowired
    private ACCatalog acCatalog;

    /**
     * UC HA.4.1: confirmTypes
     * 1: hp = getHP(HPId) -> HPCatalog
     * 2: acType = get(aircraftTypeId) -> ACTypeCatalog
     * 3: add(hp, acType) -> hp.hangarProvider
     */
    @Transactional
    public HangarProvider confirmTypes(Long hpId, List<Long> aircraftTypeIds) {
        // 1: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // Für jeden Aircraft Type
        for (Long aircraftTypeId : aircraftTypeIds) {
            // 2: Aircraft Type aus ACTypeCatalog holen
            String acType = acTypeCatalog.get(aircraftTypeId);
            
            // 3: add(hp, acType) -> hp (hp ist this, daher hp.add(acType))
            hp.add(acType);
        }
        
        return hp;
    }

    /**
     * HA.4: Spezialisierung setzen – ersetzt die aktuelle Auswahl durch aircraftTypeIds.
     */
    @Transactional
    public HangarProvider setTypes(Long hpId, List<Long> aircraftTypeIds) {
        HangarProvider hp = hpCatalog.getHP(hpId);
        hp.getAircraftTypes().clear();
        if (aircraftTypeIds != null && !aircraftTypeIds.isEmpty()) {
            for (Long id : aircraftTypeIds) {
                hp.add(acTypeCatalog.get(id));
            }
        }
        hpCatalog.update(hp);
        return hp;
    }
    
    /** HA.4 4b: Erlaubte Zeichen für Flugzeugtyp-Name */
    private static final java.util.regex.Pattern VALID_NAME_PATTERN = java.util.regex.Pattern.compile("^[a-zA-Z0-9\\s\\-äöüÄÖÜß,.]+$");

    /**
     * UC HA.4.2: addNewType. StR.L.4 4a: Duplikat → "Flugzeugtyp existiert bereits"; 4b: Ungültige Zeichen → "Ungültige Zeichen im Flugzeugtyp"
     */
    @Transactional
    public AircraftType addNewType(String name, String type, String size) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Ungültige Zeichen im Flugzeugtyp");
        String n = name.trim();
        if (!VALID_NAME_PATTERN.matcher(n).matches()) throw new IllegalArgumentException("Ungültige Zeichen im Flugzeugtyp");
        if (acTypeCatalog.existsByName(n)) throw new IllegalArgumentException("Flugzeugtyp existiert bereits");
        AircraftType aircraftType = new AircraftType(n, type != null ? type.trim() : "", size != null ? size.trim() : "");
        acTypeCatalog.add(aircraftType);
        return aircraftType;
    }
    
    /**
     * UC HA.4.3: saveSelection
     * 1: hp = get(HPId) -> HPCatalog
     * 2: ac = get(aircraft) -> ACCatalog
     * 3: add(hp, ac) -> hp.HangarProvider
     */
    @Transactional
    public HangarProvider saveSelection(Long hpId, List<Long> selectedAircraftIds) {
        // 1: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // Für jedes ausgewählte Aircraft
        for (Long aircraftId : selectedAircraftIds) {
            // 2: Aircraft aus ACCatalog holen
            Aircraft ac = acCatalog.get(aircraftId);
            
            // 3: add(hp, ac) -> hp (hp ist this, daher hp.add(ac))
            hp.add(ac);
        }
        
        return hp;
    }
}
