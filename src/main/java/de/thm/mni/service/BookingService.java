package de.thm.mni.service;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.ServiceBooking;
import de.thm.mni.repository.AircraftOwnerRepository;
import de.thm.mni.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * UC FB.8 : BookingService
 */
@Service
public class BookingService {

    @Autowired
    private ACOCatalog acoCatalog;
    
    @Autowired
    private ServiceCatalog serviceCatalog;
    
    @Autowired
    private ServiceBookingCatalog serviceBookingCatalog;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;

    /** FB.8 Schritt 8: Buchungsübersicht nach Flugzeugbesitzer-E-Mail */
    public List<ServiceBooking> getBookingsByOwnerEmail(String email) {
        AircraftOwner aco = aircraftOwnerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Flugzeugbesitzer nicht gefunden"));
        return serviceBookingCatalog.findByAircraftOwnerId(aco.getId());
    }

    /**
     * FB.8 Schritt 5–6: Zusatzservices buchen – gewählte Services, Kosten werden berechnet, Buchung angelegt.
     * Schritt 7: Hangaranbieter wird informiert (Buchung liegt im System, HP kann sie einsehen).
     */
    @Transactional
    public ServiceBooking bookZusatzservices(Long acoId, String timeWindow, List<Long> serviceIds) {
        AircraftOwner aco = acoCatalog.getACO(acoId);
        if (serviceIds == null || serviceIds.isEmpty()) {
            throw new RuntimeException("Mindestens ein Service muss ausgewählt werden");
        }
        List<de.thm.mni.model.Service> selected = new ArrayList<>();
        int totalCost = 0;
        StringBuilder names = new StringBuilder();
        for (Long sid : serviceIds) {
            de.thm.mni.model.Service s = serviceRepository.findById(sid)
                    .orElseThrow(() -> new RuntimeException("Service nicht gefunden: " + sid));
            selected.add(s);
            totalCost += (s.getPrice() != null ? s.getPrice() : 0);
            if (names.length() > 0) names.append(", ");
            names.append(s.getName());
        }
        ServiceBooking sb = new ServiceBooking("Zusatzservice", timeWindow, "BOOKED", (double) totalCost, aco, names.toString());
        serviceBookingCatalog.add(sb);
        return sb;
    }

    /**
     * UC FB.8: confirmServiceBooking
     * 1: aco := getACO(ACOId) -> ACOCatalog
     * 2: get(services) -> ServiceCatalog
     * 3: create(type, timeWindow, status, cost, aco, services) -> sb:ServiceBooking
     * 3.1: add(sb) -> ServiceBookingCatalog
     */
    @Transactional
    public ServiceBooking confirmServiceBooking(Long acoId, String type, String timeWindow, 
                                               String status, Double cost, String services) {
        // 1: AircraftOwner aus ACOCatalog holen
        AircraftOwner aco = acoCatalog.getACO(acoId);
        
        // 2: Services aus ServiceCatalog holen
        String serviceInfo = serviceCatalog.get(services);
        
        // 3: ServiceBooking erstellen
        ServiceBooking sb = new ServiceBooking(type, timeWindow, status, cost, aco, serviceInfo);
        
        // 3.1: Zum ServiceBookingCatalog hinzufügen
        serviceBookingCatalog.add(sb);
        
        return sb;
    }
}
