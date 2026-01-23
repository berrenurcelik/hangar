package de.thm.mni.service;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.ServiceRequest;
import de.thm.mni.repository.AircraftOwnerRepository;
import de.thm.mni.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC FB.1, FB.4 & FB.7: ACOCatalog
 * Im Diagramm: "2.1: add(aco)" (FB.1)
 * Im Diagramm: "3.1: add(request)" (FB.4)
 * Im Diagramm: "2: aco := getACO(ACOId)" (FB.7)
 */
@Component
public class ACOCatalog {

    @Autowired
    private AircraftOwnerRepository repository;
    
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    /**
     * Im Diagramm: "2.1: add(aco)" Nachricht (FB.1)
     */
    public void add(AircraftOwner aco) {
        repository.save(aco);
    }
    
    /**
     * Im Diagramm: "2: aco := getACO(ACOId)" Nachricht (FB.7)
     */
    public AircraftOwner getACO(Long acoId) {
        return repository.findById(acoId)
            .orElseThrow(() -> new RuntimeException("AircraftOwner nicht gefunden mit ID: " + acoId));
    }
    
    /**
     * Im Diagramm: "3.1: add(request)" Nachricht (FB.4)
     */
    public void add(ServiceRequest request) {
        serviceRequestRepository.save(request);
    }
}
