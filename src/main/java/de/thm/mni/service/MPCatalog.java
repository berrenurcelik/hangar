package de.thm.mni.service;

import de.thm.mni.model.MaintenanceProvider;
import de.thm.mni.repository.MaintenanceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC HA.6: MPCatalog
 * Im Diagramm: "3: mp = getMP(MPId)"
 */
@Component
public class MPCatalog {

    @Autowired
    private MaintenanceProviderRepository repository;

    /**
     * Im Diagramm: "3: mp = getMP(MPId)" Nachricht
     */
    public MaintenanceProvider getMP(Long mpId) {
        return repository.findById(mpId)
                .orElseThrow(() -> new RuntimeException("MaintenanceProvider nicht gefunden mit ID: " + mpId));
    }
}
