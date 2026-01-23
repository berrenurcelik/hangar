package de.thm.mni.service;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.ServiceBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UC FB.8: BookingService
 */
@Service
public class BookingService {

    @Autowired
    private ACOCatalog acoCatalog;
    
    @Autowired
    private ServiceCatalog serviceCatalog;
    
    @Autowired
    private ServiceBookingCatalog serviceBookingCatalog;

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
