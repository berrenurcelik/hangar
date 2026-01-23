package de.thm.mni.service;

import de.thm.mni.model.Offer;
import de.thm.mni.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC FB.5: OfferCatalog
 * Im Diagramm: "1: offer := getOffer(offerID)"
 */
@Component
public class OfferCatalog {

    @Autowired
    private OfferRepository repository;

    /**
     * Im Diagramm: "1: offer := getOffer(offerID)" Nachricht
     */
    public Offer getOffer(Long offerID) {
        return repository.findById(offerID)
            .orElseThrow(() -> new RuntimeException("Offer nicht gefunden mit ID: " + offerID));
    }
    
    /**
     * Offer speichern
     */
    public void add(Offer offer) {
        repository.save(offer);
    }
}
