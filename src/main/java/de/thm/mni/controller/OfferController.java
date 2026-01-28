package de.thm.mni.controller;

import de.thm.mni.model.Offer;
import de.thm.mni.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offer")
@CrossOrigin(origins = "*")
public class OfferController {
    
    @Autowired
    private OfferService offerService;
    
    /** UC FB.5: Schritt 1–2 – Meine Angebote – alle eingegangenen Angebote zum Flugzeugbesitzer */
    @GetMapping("/by-owner")
    public ResponseEntity<List<Offer>> getOffersByOwner(@RequestParam String email) {
        try {
            List<Offer> offers = offerService.getOffersByOwnerEmail(email);
            return ResponseEntity.ok(offers);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /** UC FB.5: Schritt 4 – Angebotsdetails (Dauer, Kosten, Leistungen) */
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        try {
            Offer offer = offerService.getOfferById(id);
            return ResponseEntity.ok(offer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /** UC FB.5: Schritt 5–6 – Annehmen oder Ablehnen speichern */
    @PostMapping("/save-decision")
    public ResponseEntity<Offer> saveDecision(@RequestBody Map<String, Object> request) {
        try {
            Long offerID = Long.valueOf(request.get("offerID").toString());
            String status = request.get("status").toString();
            
            Offer offer = offerService.saveDecision(offerID, status);
            return ResponseEntity.ok(offer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
