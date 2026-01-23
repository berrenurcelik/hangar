package de.thm.mni.service;

import de.thm.mni.model.AircraftAppointment;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.Parking;
import de.thm.mni.repository.AircraftAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * UC FB.7: AppointmentManager für AircraftAppointment
 */
@Service
public class AppointmentManager {

    @Autowired
    private AircraftAppointmentRepository aircraftAppointmentRepository;
    
    @Autowired
    private HPCatalog hpCatalog;
    
    @Autowired
    private ParkingCatalog parkingCatalog;

    /**
     * UC FB.7: makeAppointment
     * Erstellt einen Termin für ein Flugzeug
     */
    @Transactional
    public AircraftAppointment makeAppointment(Long hangarProviderId, Date date, Date time, 
                                               AircraftAppointment.AppointmentType type) {
        // HangarProvider abrufen
        HangarProvider hangarProvider = hpCatalog.getHP(hangarProviderId);
        
        // AircraftAppointment erstellen
        AircraftAppointment appointment = new AircraftAppointment(date, time, type);
        
        // Speichern
        return aircraftAppointmentRepository.save(appointment);
    }
    
    /**
     * UC FB.7: findTimeSlots
     * Findet verfügbare Zeitfenster für einen HangarProvider
     */
    public List<Date> findTimeSlots(Long hangarProviderId, String transactionType) {
        // HangarProvider validieren
        HangarProvider hangarProvider = hpCatalog.getHP(hangarProviderId);
        
        // Beispiel-Implementierung: Generiere Zeitfenster für die nächsten 7 Tage
        List<Date> timeSlots = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            // Füge mehrere Zeitfenster pro Tag hinzu (9:00, 12:00, 15:00)
            for (int hour : new int[]{9, 12, 15}) {
                Calendar slotTime = (Calendar) calendar.clone();
                slotTime.set(Calendar.HOUR_OF_DAY, hour);
                slotTime.set(Calendar.MINUTE, 0);
                slotTime.set(Calendar.SECOND, 0);
                timeSlots.add(slotTime.getTime());
            }
        }
        
        return timeSlots;
    }
    
    /**
     * UC FB.7: isAppointmentAvailable
     * Prüft, ob ein Termin verfügbar ist
     */
    public boolean isAppointmentAvailable(Long hangarProviderId, Date date, Date time) {
        // HangarProvider validieren
        HangarProvider hangarProvider = hpCatalog.getHP(hangarProviderId);
        
        // Prüfe, ob für diesen Zeitpunkt bereits ein Termin existiert
        // Einfache Implementierung: Immer verfügbar (kann erweitert werden)
        return true;
    }
    
    /**
     * UC FB.7: getAppointmentsByProvider
     * Ruft alle Termine für einen HangarProvider ab
     */
    public List<AircraftAppointment> getAppointmentsByProvider(Long hangarProviderId) {
        // HangarProvider validieren
        HangarProvider hangarProvider = hpCatalog.getHP(hangarProviderId);
        
        // Alle Termine abrufen (kann später gefiltert werden)
        return aircraftAppointmentRepository.findAll();
    }
}
