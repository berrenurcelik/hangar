package de.thm.mni.controller;

import de.thm.mni.model.Offer;
import de.thm.mni.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/offer")
@CrossOrigin(origins = "*")
public class OfferController {
    
    @Autowired
    private OfferService offerService;
    
    /**
     * UC FB.5: saveDecision Endpunkt
     */
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
