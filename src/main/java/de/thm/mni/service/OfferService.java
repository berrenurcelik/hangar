package de.thm.mni.service;

import de.thm.mni.model.Offer;
import de.thm.mni.repository.AircraftOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UC FB.5: OfferService – Angebot annehmen/ablehnen, Meine Angebote
 */
@Service
public class OfferService {

    @Autowired
    private OfferCatalog offerCatalog;

    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;

    /**
     * UC FB.5: saveDecision – Schritt 6 „Das System speichert die Entscheidung“
     * 1: offer := getOffer(offerID) -> OfferCatalog
     * 2: set(status) -> offer:Offer
     * 3: save(offer) -> OfferCatalog
     */
    @Transactional
    public Offer saveDecision(Long offerID, String status) {
        Offer offer = offerCatalog.getOffer(offerID);
        offer.set(status);
        return offerCatalog.save(offer);
    }

    /** UC FB.5: Schritt 2 „Das System zeigt alle eingegangenen Angebote an“ – nach E-Mail des Flugzeugbesitzers */
    public List<Offer> getOffersByOwnerEmail(String email) {
        var owner = aircraftOwnerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Flugzeugbesitzer nicht gefunden: " + email));
        return offerCatalog.findByAircraftOwnerId(owner.getId());
    }

    /** UC FB.5: Schritt 4 „Angebotsdetails (z. B. Dauer, Kosten, Leistungen)“ */
    public Offer getOfferById(Long id) {
        return offerCatalog.getOffer(id);
    }
}
