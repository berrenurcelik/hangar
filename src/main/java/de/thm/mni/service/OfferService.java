package de.thm.mni.service;

import de.thm.mni.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UC FB.5: OfferService
 */
@Service
public class OfferService {

    @Autowired
    private OfferCatalog offerCatalog;

    /**
     * UC FB.5: saveDecision
     * 1: offer := getOffer(offerID) -> OfferCatalog
     * 2: set(status) -> offer:Offer
     */
    @Transactional
    public Offer saveDecision(Long offerID, String status) {
        // 1: Offer aus OfferCatalog holen
        Offer offer = offerCatalog.getOffer(offerID);
        
        // 2: Status setzen
        offer.set(status);
        
        return offer;
    }
}
