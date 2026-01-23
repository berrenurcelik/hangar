package de.thm.mni.service;

import de.thm.mni.model.SparePart;
import de.thm.mni.repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UC FB.6: PartsManager
 */
@Service
public class PartsManager {

    @Autowired
    private PartsCatalog partsCatalog;
    
    @Autowired
    private SparePartRepository sparePartRepository;
    
    public List<SparePart> getAllParts() {
        return sparePartRepository.findAll();
    }

    /**
     * UC FB.6: searchParts
     * 1: found := find(criteria) -> PartsCatalog
     * 2a: [found = true] results(parts)
     * 2b: [found = false] noPartsFound()
     */
    public List<SparePart> searchParts(String criteria) {
        // 1: Parts aus PartsCatalog suchen
        List<SparePart> found = partsCatalog.find(criteria);
        
        // 2a: [found = true] Parts zurückgeben
        if (!found.isEmpty()) {
            return found;
        }
        
        // 2b: [found = false] Exception werfen
        throw new RuntimeException("Keine Parts gefunden für Kriterium: " + criteria);
    }
}
