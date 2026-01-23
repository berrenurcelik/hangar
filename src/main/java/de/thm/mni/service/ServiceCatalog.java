package de.thm.mni.service;

import de.thm.mni.model.Service;
import de.thm.mni.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC HA.2 & FB.8: ServiceCatalog
 * Im Diagramm: "2.1: add(service)" Nachricht (HA.2)
 * Im Diagramm: "2: get(services)" Nachricht (FB.8)
 */
@Component
public class ServiceCatalog {

    @Autowired
    private ServiceRepository repository;

    /**
     * Im Diagramm: "2.1: add(service)" Nachricht (HA.2)
     */
    public void add(Service service) {
        repository.save(service);
    }
    
    /**
     * Im Diagramm: "2: get(services)" Nachricht (FB.8)
     * Gibt Service-Informationen als String zurück
     */
    public String get(String services) {
        // Services als String zurückgeben
        return services;
    }
}
