package de.thm.mni.service;

import de.thm.mni.model.Aircraft;
import de.thm.mni.model.FlightReadiness;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.model.MaintenanceStatus;
import de.thm.mni.repository.HangarProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service-Klasse für Hangaranbieter-Logik.
 * Implementiert den Anwendungsfall HA.1 (Profil speichern).
 */
@Service
public class HPService {
    
    @Autowired
    private HangarProviderRepository hangarProviderRepository;
    
    @Autowired
    private HPCatalog hpCatalog;
    
    @Autowired
    private ServiceCatalog serviceCatalog; // Verbindung zum Katalog gemäß Diagramm
    
    @Autowired
    private ACCatalog acCatalog;
    
    @Autowired
    private StatusCatalog statusCatalog;
    
    /**
     * Implementiert UC HA.1: saveProfile
     * * Ablauf gemäß Kommunikationsdiagramm:
     * 1. create() -> Erstellt Benutzer-Teil (via super() im Konstruktor)
     * 2. create(...) -> Erstellt HangarProvider-Teil (via Konstruktor)
     * 3. add(hp) -> Fügt das Objekt dem HPCatalog hinzu
     */
    @Transactional
    public HangarProvider saveProfile(String name, String email, String password, 
                                      String contact, String serviceHours, 
                                      Set<String> storageConditions) {
        return saveProfile(name, email, password, contact, serviceHours, "", "", storageConditions);
    }
    
    @Transactional
    public HangarProvider saveProfile(String name, String email, String password, 
                                      String contact, String serviceHours, String city,
                                      Set<String> storageConditions) {
        return saveProfile(name, email, password, contact, serviceHours, city, "", storageConditions);
    }
    
    /**
     * HA.1: saveProfil(name, contacts, email, password, role, costs, serviceHours, storageConditions)
     */
    @Transactional
    public HangarProvider saveProfile(String name, String email, String password, 
                                      String contact, String serviceHours, String city, String costs,
                                      Set<String> storageConditions) {
        
        // Schritt 1 & 2: Erzeugung der Instanz (Benutzer + HangarProvider)
        HangarProvider hangarProvider = new HangarProvider(
                name, email, password, contact, serviceHours, city, costs, storageConditions
        );
        
        // Schritt 3: Hinzufügen zum Katalog
        hpCatalog.add(hangarProvider);
        
        return hangarProvider;
    }
    
    public HangarProvider getProfile(Long id) {
        return hangarProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HangarProvider nicht gefunden"));
    }
    
    public List<HangarProvider> getAllProfiles() {
        return hangarProviderRepository.findAll();
    }
    
    public void deleteProfile(Long id) {
        HangarProvider provider = getProfile(id);
        hpCatalog.remove(provider);
    }
    
    /**
     * UC HA.2: saveService
     * 1: hp := getHP(HPId) -> HPCatalog
     * 2: create(hp, name, description, cost, conditions) -> Service
     * 2.1: add(service) -> ServiceCatalog
     */
    @Transactional
    public de.thm.mni.model.Service saveService(Long hpId, String name, String description, 
                                                 Integer cost, Set<String> conditions) {
        // 1: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // 2: Service mit HangarProvider erstellen
        de.thm.mni.model.Service service = new de.thm.mni.model.Service(hp, name, description, cost, conditions);
        
        // 2.1: Zum ServiceCatalog hinzufügen (repository.save wird ausgeführt)
        serviceCatalog.add(service);
        
        return service;
    }
    
    /**
     * UC HA.5: saveInput
     * 1: hp := getHP(HPId) -> HPCatalog
     * 2: ac := getAC(ACId) -> AircraftCatalog
     * 3: create(maintenance, hp, ac) -> mt:MaintenanceStatus
     * 4: create(flightReadiness, hp, ac) -> fr:FlightReadiness
     * 5: add(mt, fr) -> StatusCatalog
     */
    @Transactional
    public void saveInput(Long hpId, Long acId, String maintenance, String flightReadiness) {
        // 1: HangarProvider aus HPCatalog holen
        HangarProvider hp = hpCatalog.getHP(hpId);
        
        // 2: Aircraft aus ACCatalog holen
        Aircraft ac = acCatalog.get(acId);
        
        // 3: MaintenanceStatus erstellen
        MaintenanceStatus mt = new MaintenanceStatus(maintenance, hp, ac);
        
        // 4: FlightReadiness erstellen
        FlightReadiness fr = new FlightReadiness(flightReadiness, hp, ac);
        
        // 5: Beide zum StatusCatalog hinzufügen
        statusCatalog.add(mt, fr);
    }

    /** HA.5: eingelagerte Flugzeuge (dem Hangaranbieterprofil zugeordnet) */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Aircraft> getAircraftInHangarByEmail(String email) {
        HangarProvider hp = hpCatalog.getHPByEmail(email);
        return new ArrayList<>(hp.getSelectedAircrafts());
    }

    /** HA.5: saveInput per E-Mail (HP wird über email ermittelt) */
    @Transactional
    public void saveInputByProvider(String email, Long aircraftId, String maintenance, String flightReadiness) {
        HangarProvider hp = hpCatalog.getHPByEmail(email);
        saveInput(hp.getId(), aircraftId, maintenance, flightReadiness);
    }
}