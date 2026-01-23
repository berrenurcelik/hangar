package de.thm.mni.service;

import de.thm.mni.model.HangarProvider;
import de.thm.mni.repository.HangarProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HPCatalog {

    @Autowired
    private HangarProviderRepository repository;

    /**
     * Im Diagramm: "3: add(hp)" Nachricht (HA.1)
     */
    public void add(HangarProvider hp) {
        repository.save(hp);
    }
    
    /**
     * Im Diagramm: "1: hp := getHP(HPId)" Nachricht (HA.2)
     */
    public HangarProvider getHP(Long hpId) {
        return repository.findById(hpId)
                .orElseThrow(() -> new RuntimeException("HangarProvider nicht gefunden mit ID: " + hpId));
    }
    
    /**
     * Remove HangarProvider (for deleteProfile)
     */
    public void remove(HangarProvider provider) {
        repository.delete(provider);
    }
}
