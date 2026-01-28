package de.thm.mni.service;

import de.thm.mni.model.Appointment;
import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.HandoverReturnAppointment;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.MaintenanceProvider;
import de.thm.mni.model.ServiceRequest;
import de.thm.mni.repository.AircraftOwnerRepository;
import de.thm.mni.repository.HangarProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * UC HA.7 & FB.7: AppointmentService
 */
@Service
public class AppointmentService {

    @Autowired
    private SRequestCatalog sRequestCatalog;
    
    @Autowired
    private HPCatalog hpCatalog;
    
    @Autowired
    private MPCatalog mpCatalog;
    
    @Autowired
    private AppointmentCatalog appointmentCatalog;
    
    @Autowired
    private ACOCatalog acoCatalog;
    
    @Autowired
    private HandoverReturnAppointmentCatalog handoverReturnAppointmentCatalog;

    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;

    @Autowired
    private HangarProviderRepository hangarProviderRepository;

    @Autowired
    private de.thm.mni.repository.AircraftRepository aircraftRepository;

    /**
     * UC HA.7: saveAppointment
     * 1: sr := getSR(SRId) -> SRequestCatalog
     * 2: hp := getHP(HPId) -> HPCatalog 
     * 3: mp := getMP(MPId) -> MPCatalog 
     * 4: create(date, status, sr, hp, mp) -> app:Appointment
     * 4.1: add(app) -> AppointmentCatalog
     */
    @Transactional
    public Appointment saveAppointment(Long srId, Date date, String status) {
        // 1: ServiceRequest aus SRequestCatalog holen
        ServiceRequest sr = sRequestCatalog.getSR(srId);
        
        // 2: HangarProvider aus ServiceRequest holen
        HangarProvider hp = sr.getHangarProvider();
        
        // 3: MaintenanceProvider aus ServiceRequest holen
        MaintenanceProvider mp = sr.getMaintenanceProvider();
        
        // 4: Appointment erstellen
        Appointment app = new Appointment(date, status, sr, hp, mp);
        
        // 4.1: Zum AppointmentCatalog hinzufügen
        appointmentCatalog.add(app);
        
        // HA.7: Wartungsstatus des Flugzeugs aktualisieren (vereinfachte Variante)
        if (sr.getAircraft() != null) {
            var ac = sr.getAircraft();
            ac.setMaintenanceStatus("Wartungstermin vereinbart am " + date);
            aircraftRepository.save(ac);
        }
        
        return app;
    }
    
    /** HA.7 Schritt 2: offene/akzeptierte Anfragen für Terminplanung */
    public java.util.List<ServiceRequest> getServiceRequestsForProvider(Long hpId) {
        hpCatalog.getHP(hpId); // Existenz prüfen
        return sRequestCatalog.findForHangarProvider(hpId);
    }
    
    /**
     * HA.7 Schritt 2: bestehende Buchungen (Übergabe-/Rückgabetermine) des Flugzeugbesitzers
     */
    public List<HandoverReturnAppointment> getHandoverAppointmentsByOwnerEmail(String email) {
        AircraftOwner owner = aircraftOwnerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Flugzeugbesitzer nicht gefunden"));
        return handoverReturnAppointmentCatalog.findByAircraftOwnerId(owner.getId());
    }

    /**
     * HA.7 Schritt 6/5a1: verfügbare Zeitfenster für Hangaranbieter (belegte Slots ausgeschlossen)
     */
    public List<Date> getAvailableSlots(Long hangarProviderId) {
        hpCatalog.getHP(hangarProviderId);
        List<HandoverReturnAppointment> existing = handoverReturnAppointmentCatalog.findByHangarProviderId(hangarProviderId);
        existing = existing.stream()
                .filter(a -> HandoverReturnAppointment.STATUS_PENDING.equals(a.getStatus()) || HandoverReturnAppointment.STATUS_CONFIRMED.equals(a.getStatus()))
                .toList();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        List<Date> slots = new ArrayList<>();
        for (int day = 0; day < 14; day++) {
            for (int hour = 8; hour <= 18; hour++) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                if (cal.getTime().before(new Date())) continue;
                Date slot = cal.getTime();
                boolean taken = existing.stream().anyMatch(a -> sameSlot(a.getDate(), slot));
                if (!taken) slots.add(slot);
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return slots;
    }

    private static boolean sameSlot(Date a, Date b) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(a);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(b);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
            && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
            && c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * HangarProvider: ausstehende Terminanfragen (Status PENDING)
     */
    public List<HandoverReturnAppointment> getPendingByHangarProviderEmail(String email) {
        HangarProvider hp = hangarProviderRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Hangaranbieter nicht gefunden"));
        return handoverReturnAppointmentCatalog.findByHangarProviderIdAndStatus(hp.getId(), HandoverReturnAppointment.STATUS_PENDING);
    }

    /**
     * HangarProvider bestätigt Termin – nur der zuständige HP darf bestätigen
     */
    @Transactional
    public HandoverReturnAppointment confirmHandover(Long appointmentId, String hangarProviderEmail) {
        HandoverReturnAppointment app = handoverReturnAppointmentCatalog.getById(appointmentId);
        if (app.getStatus() != null && !HandoverReturnAppointment.STATUS_PENDING.equals(app.getStatus())) {
            throw new RuntimeException("Termin ist nicht mehr offen zur Bestätigung");
        }
        HangarProvider hp = hangarProviderRepository.findByEmail(hangarProviderEmail)
                .orElseThrow(() -> new RuntimeException("Hangaranbieter nicht gefunden"));
        if (!hp.getId().equals(app.getHangarProvider().getId())) {
            throw new RuntimeException("Nur der zuständige Hangaranbieter darf bestätigen");
        }
        app.setStatus(HandoverReturnAppointment.STATUS_CONFIRMED);
        return handoverReturnAppointmentCatalog.save(app);
    }

    /**
     * HangarProvider lehnt Termin ab
     */
    @Transactional
    public HandoverReturnAppointment rejectHandover(Long appointmentId, String hangarProviderEmail) {
        HandoverReturnAppointment app = handoverReturnAppointmentCatalog.getById(appointmentId);
        if (app.getStatus() != null && !HandoverReturnAppointment.STATUS_PENDING.equals(app.getStatus())) {
            throw new RuntimeException("Termin ist nicht mehr offen");
        }
        HangarProvider hp = hangarProviderRepository.findByEmail(hangarProviderEmail)
                .orElseThrow(() -> new RuntimeException("Hangaranbieter nicht gefunden"));
        if (!hp.getId().equals(app.getHangarProvider().getId())) {
            throw new RuntimeException("Nur der zuständige Hangaranbieter darf ablehnen");
        }
        app.setStatus(HandoverReturnAppointment.STATUS_REJECTED);
        return handoverReturnAppointmentCatalog.save(app);
    }

    /**
     * UC FB.7 : saveAppointment – Übergabe- oder Rückgabetermin (Status initial PENDING)
     * FB.7 Schritt 9/7a1: Prüft Verfügbarkeit; 7a2: wirft bei Belegung „Termin kann nicht bestätigt werden“
     */
    @Transactional
    public HandoverReturnAppointment saveHandoverAppointment(Long hpId, Long acoId, Date date, String status, String type) {
        List<HandoverReturnAppointment> existing = handoverReturnAppointmentCatalog.findByHangarProviderId(hpId).stream()
                .filter(a -> HandoverReturnAppointment.STATUS_PENDING.equals(a.getStatus()) || HandoverReturnAppointment.STATUS_CONFIRMED.equals(a.getStatus()))
                .toList();
        boolean taken = existing.stream().anyMatch(a -> sameSlot(a.getDate(), date));
        if (taken) {
            throw new RuntimeException("Termin kann nicht bestätigt werden");
        }
        HangarProvider hp = hpCatalog.getHP(hpId);
        AircraftOwner aco = acoCatalog.getACO(acoId);
        HandoverReturnAppointment app = new HandoverReturnAppointment(date, status, type, hp, aco);
        handoverReturnAppointmentCatalog.add(app);
        return app;
    }
}