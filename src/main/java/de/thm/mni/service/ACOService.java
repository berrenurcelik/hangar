package de.thm.mni.service;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UC FB.1: ACOService (AircraftOwner Service)
 */
@Service
public class ACOService {

    @Autowired
    private ACOCatalog acoCatalog;

    /**
     * UC FB.1: saveProfile
     * 1: create(name, contacts, email, password, role) -> u:User
     * 2: create(u) -> aco:AircraftOwner
     * 2.1: add(aco) -> ACOCatalog
     */
    @Transactional
    public AircraftOwner saveProfile(String name, String contacts, String email, 
                                     String password, Benutzer.Role role) {
        // 1: User (Benutzer) wird im AircraftOwner-Konstruktor erstellt (via super())
        // 2: AircraftOwner erstellen
        AircraftOwner aco = new AircraftOwner(name, email, password, contacts);
        
        // 2.1: Zum ACOCatalog hinzufügen
        acoCatalog.add(aco);
        
        return aco;
    }
}
