package de.thm.mni.service;

import de.thm.mni.model.Appointment;
import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.HandoverReturnAppointment;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.MaintenanceProvider;
import de.thm.mni.model.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        
        return app;
    }
    
    /**
     * UC FB.7: saveAppointment
     * 1: hp := getHP(HPID) -> HPCatalog
     * 2: aco := getACO(ACOId) -> ACOCatalog
     * 3: create(date, status, hp, aco) -> app:HandoverReturnAppointment
     * 3.1: add(app) -> HandoverReturnAppointmentCatalog
     */
    @Transactional
    public HandoverReturnAppointment saveAppointment(Long hpId, Long acoId, Date date, String status) {
        // 1: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // 2: AircraftOwner aus ACOCatalog holen
        AircraftOwner aco = acoCatalog.getACO(acoId);
        
        // 3: HandoverReturnAppointment erstellen
        HandoverReturnAppointment app = new HandoverReturnAppointment(date, status, hp, aco);
        
        // 3.1: Zum HandoverReturnAppointmentCatalog hinzufügen
        handoverReturnAppointmentCatalog.add(app);
        
        return app;
    }
}