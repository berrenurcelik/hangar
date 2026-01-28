package de.thm.mni.service;

import de.thm.mni.model.SparePart;
import de.thm.mni.repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UC FB.6 & FB.10: PartsCatalog (SparePartCatalog)
 * Im Diagramm: "1: found := find(criteria)" (FB.6)
 * Im Diagramm: "3: sp := getSparePart(sparePartID)" (FB.10)
 */
@Component
public class PartsCatalog {

    @Autowired
    private SparePartRepository repository;

    /**
     * UC FB.6: Im Diagramm "1: found := find(criteria)" Nachricht
     */
    public List<SparePart> find(String criteria) {
        return repository.findByNameContainingOrDescriptionContaining(criteria, criteria);
    }
    
    /**
     * UC FB.10: Im Diagramm "3: sp := getSparePart(sparePartID)" Nachricht
     */
    public SparePart getSparePart(Long sparePartID) {
        return repository.findById(sparePartID)
            .orElseThrow(() -> new RuntimeException("SparePart nicht gefunden mit ID: " + sparePartID));
    }

    /**
     * StR.EA.3 Schritt 6: Artikelbestand nach Reservierung anpassen
     */
    public void update(SparePart sp) {
        repository.save(sp);
    }
}
