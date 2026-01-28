package de.thm.mni.controller;

import de.thm.mni.model.HandoverReturnAppointment;
import de.thm.mni.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FB.7: Übergabe- und Rückgabetermine – Flugzeugbesitzer
 */
@RestController
@RequestMapping("/api/handover-appointments")
@CrossOrigin(origins = "*")
public class HandoverAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /** FB.7: bestehende Buchungen des Flugzeugbesitzers */
    @GetMapping("/by-owner")
    public ResponseEntity<List<HandoverReturnAppointment>> getByOwner(@RequestParam String email) {
        try {
            List<HandoverReturnAppointment> list = appointmentService.getHandoverAppointmentsByOwnerEmail(email);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** FB.7: verfügbare Zeitfenster für einen Hangaranbieter */
    @GetMapping("/available-slots")
    public ResponseEntity<List<java.util.Date>> getAvailableSlots(@RequestParam Long hangarProviderId) {
        try {
            List<java.util.Date> slots = appointmentService.getAvailableSlots(hangarProviderId);
            return ResponseEntity.ok(slots);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** HangarProvider: ausstehende Terminanfragen (Status PENDING) – interaktive Bestätigung */
    @GetMapping("/pending-for-provider")
    public ResponseEntity<List<HandoverReturnAppointment>> getPendingForProvider(@RequestParam String email) {
        try {
            List<HandoverReturnAppointment> list = appointmentService.getPendingByHangarProviderEmail(email);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** HangarProvider bestätigt Termin – Status wird CONFIRMED */
    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@PathVariable Long id, @RequestParam String email) {
        try {
            HandoverReturnAppointment app = appointmentService.confirmHandover(id, email);
            return ResponseEntity.ok(app);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }

    /** HangarProvider lehnt Termin ab – Status wird REJECTED */
    @PostMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id, @RequestParam String email) {
        try {
            HandoverReturnAppointment app = appointmentService.rejectHandover(id, email);
            return ResponseEntity.ok(app);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }
}
