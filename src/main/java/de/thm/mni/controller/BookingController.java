package de.thm.mni.controller;

import de.thm.mni.model.ServiceBooking;
import de.thm.mni.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "*")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    /** FB.8: Buchungsübersicht des Flugzeugbesitzers */
    @GetMapping("/by-owner")
    public ResponseEntity<List<ServiceBooking>> getByOwner(@RequestParam String email) {
        try {
            List<ServiceBooking> list = bookingService.getBookingsByOwnerEmail(email);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /** FB.8: Zusatzservices buchen – serviceIds, timeWindow, acoId */
    @PostMapping("/book-zusatzservices")
    public ResponseEntity<ServiceBooking> bookZusatzservices(@RequestBody Map<String, Object> request) {
        try {
            Long acoId = Long.valueOf(request.get("acoId").toString());
            String timeWindow = request.get("timeWindow") != null ? request.get("timeWindow").toString() : "";
            @SuppressWarnings("unchecked")
            List<Number> ids = (List<Number>) request.get("serviceIds");
            List<Long> serviceIds = new ArrayList<>();
            if (ids != null) for (Number n : ids) serviceIds.add(n.longValue());
            ServiceBooking sb = bookingService.bookZusatzservices(acoId, timeWindow, serviceIds);
            return ResponseEntity.ok(sb);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
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
