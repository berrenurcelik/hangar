package de.thm.mni.service;

import de.thm.mni.model.ServiceRequest;
import de.thm.mni.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC HA.6 & HA.7: SRequestCatalog
 * Im Diagramm: "4.1: add(sr)" (HA.6)
 * Im Diagramm: "1: sr := getSR(SRId)" (HA.7)
 */
@Component
public class SRequestCatalog {

    @Autowired
    private ServiceRequestRepository repository;

    /**
     * Im Diagramm: "4.1: add(sr)" Nachricht (HA.6)
     */
    public void add(ServiceRequest sr) {
        repository.save(sr);
    }
    
    /**
     * Im Diagramm: "1: sr := getSR(SRId)" Nachricht (HA.7)
     */
    public ServiceRequest getSR(Long srId) {
        return repository.findById(srId)
                .orElseThrow(() -> new RuntimeException("ServiceRequest nicht gefunden mit ID: " + srId));
    }
}
