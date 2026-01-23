package de.thm.mni.service;

import de.thm.mni.model.HangarProvider;
import de.thm.mni.repository.HangarProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC HA.3: HangarCatalog
 * Im Diagramm: "1: hangar := getHangar(hangarId)"
 */
@Component
public class HangarCatalog {

    @Autowired
    private HangarProviderRepository repository;

    /**
     * Im Diagramm: "1: hangar := getHangar(hangarId)" Nachricht (HA.3)
     */
    public HangarProvider getHangar(Long hangarId) {
        return repository.findById(hangarId)
                .orElseThrow(() -> new RuntimeException("HangarProvider nicht gefunden mit ID: " + hangarId));
    }
}
