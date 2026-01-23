package de.thm.mni.service;

import de.thm.mni.model.PartsProvider;
import de.thm.mni.repository.PartsProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC FB.10: PSCatalog (PartsProviderCatalog)
 * Im Diagramm: "2: ps := getPS(PSId)"
 */
@Component
public class PSCatalog {

    @Autowired
    private PartsProviderRepository repository;

    /**
     * Im Diagramm: "2: ps := getPS(PSId)" Nachricht
     */
    public PartsProvider getPS(Long psId) {
        return repository.findById(psId)
            .orElseThrow(() -> new RuntimeException("PartsProvider nicht gefunden mit ID: " + psId));
    }
}
