package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class HangarProvider extends Benutzer {
    
    private String serviceHours;
    
    @ElementCollection
    private Set<String> storageConditions = new HashSet<>();
    
    @ElementCollection
    private Set<String> aircraftTypes = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "hangar_provider_aircraft",
        joinColumns = @JoinColumn(name = "hangar_provider_id"),
        inverseJoinColumns = @JoinColumn(name = "aircraft_id")
    )
    @JsonIgnoreProperties({"aircraftOwner", "aircraftAppointments"})
    private Set<Aircraft> selectedAircrafts = new HashSet<>();
    
    @OneToMany(mappedBy = "hangarProvider", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hangarProvider", "aircraftAppointments"})
    private Set<Parking> parkings = new HashSet<>();
    
    @OneToMany(mappedBy = "hangarProvider", cascade = CascadeType.ALL)
    private Set<Service> services = new HashSet<>();
    
    @OneToMany(mappedBy = "hangarProvider", cascade = CascadeType.ALL)
    private Set<RepairRequest> repairRequests = new HashSet<>();
    
    // Leerer Konstruktor für JPA
    public HangarProvider() {
        super();
    }

   /**
     * Konstruktor für das Erstellen eines neuen Hangaranbieters.
     * (KD.HA.1)
     * * 1. super(...) -> Erstellt den Benutzer-Teil.
     * 2. this.set... -> Setzt die spezifischen HangarProvider-Attribute.
     */
    public HangarProvider(String name, String email, String password, String contact, 
                          String serviceHours, Set<String> storageConditions) {
        // Schritt 1: Benutzer erstellen
        super(name, email, password, contact, Role.HANGAR_PROVIDER);
        
        // Schritt 2: HangarProvider-spezifische Daten setzen
        this.serviceHours = serviceHours;
        this.storageConditions = storageConditions;
    }
    
    // Business Methods
    public void saveProfile() {
        // Implementation
    }
    
    public void saveService() {
        // Implementation
    }
    
    public void updateParking() {
        // Implementation
    }
    
    public void requestAircraftType() {
        // Implementation
    }
    
    public void updateAircraftStatus() {
        // Implementation
    }
    
    public void createMaintenanceRecord() {
        // Implementation
    }
    
    public void createRepairAppointment() {
        // Implementation
    }
    
    public void disableFollowing() {
        // Implementation
    }
    
    public void createOffer() {
        // Implementation
    }
    
    // Getters and Setters
    public String getServiceHours() {
        return serviceHours;
    }
    
    public void setServiceHours(String serviceHours) {
        this.serviceHours = serviceHours;
    }
    
    public Set<String> getStorageConditions() {
        return storageConditions;
    }
    
    public void setStorageConditions(Set<String> storageConditions) {
        this.storageConditions = storageConditions;
    }
    
    public Set<Parking> getParkings() {
        return parkings;
    }
    
    public void setParkings(Set<Parking> parkings) {
        this.parkings = parkings;
    }
    
    public Set<Service> getServices() {
        return services;
    }
    
    public void setServices(Set<Service> services) {
        this.services = services;
    }
    
    public Set<RepairRequest> getRepairRequests() {
        return repairRequests;
    }
    
    public void setRepairRequests(Set<RepairRequest> repairRequests) {
        this.repairRequests = repairRequests;
    }
    
    public Set<String> getAircraftTypes() {
        return aircraftTypes;
    }
    
    public void setAircraftTypes(Set<String> aircraftTypes) {
        this.aircraftTypes = aircraftTypes;
    }
    
    /**
     * UC HA.4.1: Im Diagramm "3: add(hp, acType)"
     */
    public void add(String acType) {
        this.aircraftTypes.add(acType);
    }
    
    public Set<Aircraft> getSelectedAircrafts() {
        return selectedAircrafts;
    }
    
    public void setSelectedAircrafts(Set<Aircraft> selectedAircrafts) {
        this.selectedAircrafts = selectedAircrafts;
    }
    
    /**
     * UC HA.4.3: Im Diagramm "3: add(hp, ac)"
     */
    public void add(Aircraft aircraft) {
        this.selectedAircrafts.add(aircraft);
    }
}
