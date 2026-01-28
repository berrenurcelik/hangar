package de.thm.mni.controller;

import de.thm.mni.model.MaintenanceProvider;
import de.thm.mni.repository.MaintenanceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-providers")
@CrossOrigin(origins = "*")
public class MaintenanceProviderController {

    @Autowired
    private MaintenanceProviderRepository maintenanceProviderRepository;

    /**
     * HA.6: passende Werkstätten anzeigen (vereinfachte Variante – alle registrierten Werkstätten)
     */
    @GetMapping
    public ResponseEntity<List<MaintenanceProvider>> getAll() {
        List<MaintenanceProvider> list = maintenanceProviderRepository.findAll();
        return ResponseEntity.ok(list);
    }
}

