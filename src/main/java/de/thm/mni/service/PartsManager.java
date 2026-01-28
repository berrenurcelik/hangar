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
     * UC FB.6 / StR.E.6: searchParts
     * 1: found := find(criteria) -> PartsCatalog
     * 2a: [found = true] results(parts)
     * 2b: [found = false] leere Liste (5a1: „keine Ergebnisse“ – weiter mit 2)
     */
    public List<SparePart> searchParts(String criteria) {
        if (criteria == null || criteria.isBlank()) {
            return sparePartRepository.findAll();
        }
        return partsCatalog.find(criteria.trim());
    }

    /** FB.6 Schritt 7: Detailinformationen des Artikels */
    public SparePart getPartById(Long id) {
        return partsCatalog.getSparePart(id);
    }
}
