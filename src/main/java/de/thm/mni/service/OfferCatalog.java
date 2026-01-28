package de.thm.mni.service;

import de.thm.mni.model.Offer;
import de.thm.mni.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UC FB.5: OfferCatalog
 * Im Diagramm: "1: offer := getOffer(offerID)"
 */
@Component
public class OfferCatalog {

    @Autowired
    private OfferRepository repository;

    public Offer getOffer(Long offerID) {
        return repository.findById(offerID)
            .orElseThrow(() -> new RuntimeException("Offer nicht gefunden mit ID: " + offerID));
    }
    
    public void add(Offer offer) {
        repository.save(offer);
    }
    
    /** UC FB.5: Angebot nach Speichern der Entscheidung persistieren */
    public Offer save(Offer offer) {
        return repository.save(offer);
    }
    
    /** UC FB.5: Meine Angebote – alle Angebote zum Flugzeugbesitzer (über ServiceRequest → Aircraft → Owner) */
    public List<Offer> findByAircraftOwnerId(Long ownerId) {
        return repository.findByServiceRequest_Aircraft_AircraftOwner_Id(ownerId);
    }
}
