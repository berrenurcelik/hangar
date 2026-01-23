package de.thm.mni.service;

import de.thm.mni.model.Aircraft;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.MaintenanceProvider;
import de.thm.mni.model.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UC HA.6 & FB.4: SRequestManagement
 */
@Service
public class SRequestManagement {

    @Autowired
    private ACCatalog acCatalog;
    
    @Autowired
    private HPCatalog hpCatalog;
    
    @Autowired
    private MPCatalog mpCatalog;
    
    @Autowired
    private SRequestCatalog sRequestCatalog;
    
    @Autowired
    private ACOCatalog acoCatalog;

    /**
     * UC HA.6: saveRequest
     * 1: ac = getAC(ACId) -> ACCatalog
     * 2: hp = getHP(HPId) -> HPCatalog
     * 3: mp = getMP(MPId) -> MPCatalog
     * 4: create(services, location, ac, hp, mp) -> sr:ServiceRequest
     * 4.1: add(sr) -> SRequestCatalog
     */
    @Transactional
    public ServiceRequest saveRequest(Long acId, Long hpId, Long mpId, 
                                     String services, String location) {
        // 1: Aircraft aus ACCatalog holen
        Aircraft ac = acCatalog.get(acId);
        
        // 2: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // 3: MaintenanceProvider aus MPCatalog holen
        MaintenanceProvider mp = mpCatalog.getMP(mpId);
        
        // 4: ServiceRequest erstellen
        ServiceRequest sr = new ServiceRequest(services, location, ac, hp, mp);
        
        // 4.1: Zum SRequestCatalog hinzufügen
        sRequestCatalog.add(sr);
        
        return sr;
    }
    
    /**
     * UC FB.4: saveRequest
     * 1: hp:= getHP(HPID) -> HPCatalog
     * 2: ac:= getAC(ACId) -> AircraftCatalog
     * 3: create(duration, services, hp, ac) -> request:ServiceRequest
     * 3.1: add(request) -> ACOCatalog
     */
    @Transactional
    public ServiceRequest saveRequest(Long hpId, Long acId, String duration, String services) {
        // 1: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // 2: Aircraft aus ACCatalog holen (AircraftCatalog)
        Aircraft ac = acCatalog.get(acId);
        
        // 3: ServiceRequest erstellen
        ServiceRequest request = new ServiceRequest(duration, services, hp, ac);
        
        // 3.1: Zum ACOCatalog hinzufügen
        acoCatalog.add(request);
        
        return request;
    }
}
