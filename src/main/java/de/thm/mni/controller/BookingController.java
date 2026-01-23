package de.thm.mni.controller;

import de.thm.mni.model.ServiceBooking;
import de.thm.mni.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "*")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    /**
     * UC FB.8: confirmServiceBooking Endpunkt
     */
    @PostMapping("/confirm-service")
    public ResponseEntity<ServiceBooking> confirmServiceBooking(@RequestBody Map<String, Object> request) {
        try {
            Long acoId = Long.valueOf(request.get("acoId").toString());
            String type = request.get("type").toString();
            String timeWindow = request.get("timeWindow").toString();
            String status = request.get("status").toString();
            Double cost = Double.valueOf(request.get("cost").toString());
            String services = request.get("services").toString();
            
            ServiceBooking sb = bookingService.confirmServiceBooking(acoId, type, timeWindow, status, cost, services);
            return ResponseEntity.ok(sb);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
