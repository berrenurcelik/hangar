package de.thm.mni.controller;

import de.thm.mni.model.AircraftAppointment;
import de.thm.mni.model.Appointment;
import de.thm.mni.model.HandoverReturnAppointment;
import de.thm.mni.model.ServiceRequest;
import de.thm.mni.service.AppointmentManager;
import de.thm.mni.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentManager appointmentManager;
    
    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * UC FB.7: makeAppointment Endpunkt
     */
    @PostMapping("/make")
    public ResponseEntity<AircraftAppointment> makeAppointment(@RequestBody Map<String, Object> request) {
        try {
            Long hangarProviderId = Long.valueOf(request.get("hangarProviderId").toString());
            Date date = new Date(Long.valueOf(request.get("date").toString()));
            Date time = new Date(Long.valueOf(request.get("time").toString()));
            String typeStr = request.get("type").toString();
            
            AircraftAppointment.AppointmentType type = 
                AircraftAppointment.AppointmentType.valueOf(typeStr);
            
            AircraftAppointment appointment = appointmentManager.makeAppointment(
                hangarProviderId, date, time, type
            );
            
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * UC FB.7: Verfügbare Zeitfenster prüfen
     */
    @GetMapping("/time-slots/{hangarProviderId}")
    public ResponseEntity<List<Date>> getTimeSlots(
            @PathVariable Long hangarProviderId,
            @RequestParam String transactionType) {
        try {
            List<Date> slots = appointmentManager.findTimeSlots(hangarProviderId, transactionType);
            return ResponseEntity.ok(slots);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Prüfen, ob Termin verfügbar ist
     */
    @GetMapping("/check-availability/{hangarProviderId}")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable Long hangarProviderId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") Date time) {
        try {
            boolean available = appointmentManager.isAppointmentAvailable(
                hangarProviderId, date, time
            );
            return ResponseEntity.ok(available);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Alle Termine für einen Provider abrufen
     */
    @GetMapping("/provider/{hangarProviderId}")
    public ResponseEntity<List<AircraftAppointment>> getProviderAppointments(
            @PathVariable Long hangarProviderId) {
        try {
            List<AircraftAppointment> appointments = 
                appointmentManager.getAppointmentsByProvider(hangarProviderId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * UC HA.7: saveAppointment Endpunkt
     */
    @PostMapping("/save")
    public ResponseEntity<Appointment> saveAppointment(@RequestBody Map<String, Object> request) {
        try {
            Long srId = Long.valueOf(request.get("srId").toString());
            String status = request.get("status").toString();
            
            // Date parsing
            Date date;
            Object dateObj = request.get("date");
            if (dateObj instanceof Long) {
                date = new Date((Long) dateObj);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(dateObj.toString());
            }
            
            Appointment app = appointmentService.saveAppointment(srId, date, status);
            return ResponseEntity.ok(app);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * StR.L.7 Schritt 2: offene/akzeptierte Reparatur- und Wartungsanfragen für einen Hangaranbieter
     */
    @GetMapping("/maintenance-requests/{hpId}")
    public ResponseEntity<java.util.List<ServiceRequest>> getMaintenanceRequestsForProvider(@PathVariable Long hpId) {
        try {
            java.util.List<ServiceRequest> list = appointmentService.getServiceRequestsForProvider(hpId);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * UC FB.7: Übergabe- oder Rückgabetermin speichern
     * type = HANDOVER | RETURN. Bei Belegung : Termin kann nicht bestätigt werden
     */
    @PostMapping("/save-handover")
    public ResponseEntity<?> saveHandoverAppointment(@RequestBody Map<String, Object> request) {
        try {
            Long hpId = Long.valueOf(request.get("hpId").toString());
            Long acoId = Long.valueOf(request.get("acoId").toString());
            String status = request.get("status") != null ? request.get("status").toString() : "PENDING";
            String type = request.get("type") != null ? request.get("type").toString() : "HANDOVER";
            
            Date date;
            Object dateObj = request.get("date");
            if (dateObj instanceof Number) {
                date = new Date(((Number) dateObj).longValue());
            } else if (dateObj instanceof String) {
                String s = (String) dateObj;
                if (s.contains("T")) {
                    date = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(s);
                } else {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(s.length() > 10 ? s : s + " 09:00");
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
            
            HandoverReturnAppointment app = appointmentService.saveHandoverAppointment(hpId, acoId, date, status, type);
            return ResponseEntity.ok(app);
        } catch (RuntimeException e) {
            if ("Termin kann nicht bestätigt werden".equals(e.getMessage())) {
                return ResponseEntity.status(409).body(java.util.Map.of("error", e.getMessage()));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
