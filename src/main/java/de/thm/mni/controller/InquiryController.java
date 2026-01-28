package de.thm.mni.controller;

import de.thm.mni.dto.ErrorMessage;
import de.thm.mni.dto.InquiryRequest;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.Parking;
import de.thm.mni.repository.AircraftOwnerRepository;
import de.thm.mni.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UC FB.3: Anfrage an Hangaranbieter
 */
@RestController
@RequestMapping("/api/inquiry")
@CrossOrigin(origins = "*")
public class InquiryController {
    
    @Autowired
    private ParkingRepository parkingRepository;
    
    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;
    
    /**
     * UC FB.3: Anfrage an Hangaranbieter stellen
     * Flugzeugbesitzer kann eine Anfrage an den Hangaranbieter stellen
     */
    @PostMapping
    public ResponseEntity<?> sendInquiry(@RequestBody InquiryRequest request) {
        try {
            // Parking und HangarProvider prüfen
            Parking parking = parkingRepository.findById(request.parkingId())
                    .orElseThrow(() -> new RuntimeException("Parking nicht gefunden"));
            
            HangarProvider hangarProvider = parking.getHangarProvider();
            if (hangarProvider == null) {
                throw new RuntimeException("Hangaranbieter nicht gefunden");
            }
            
            // AircraftOwner prüfen
            aircraftOwnerRepository.findByEmail(request.aircraftOwnerEmail())
                    .orElseThrow(() -> new RuntimeException("Flugzeugbesitzer nicht gefunden"));
            
            // Hier würde normalerweise eine Inquiry/Message gespeichert werden
            // Für jetzt geben wir nur eine Bestätigung zurück
            return ResponseEntity.ok(new InquiryResponse(
                    true,
                    "Anfrage erfolgreich an " + hangarProvider.getName() + " gesendet",
                    hangarProvider.getEmail(),
                    request.message()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }
    
    public record InquiryResponse(boolean success, String message, String hangarProviderEmail, String inquiryMessage) {}
}
