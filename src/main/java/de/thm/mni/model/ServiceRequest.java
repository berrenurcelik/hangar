package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * UC HA.6 & FB.4: ServiceRequest Model
 */
@Entity
public class ServiceRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String services;
    private String location;
    private String duration;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    @JsonIgnoreProperties({"aircraftAppointments", "aircraftOwner"})
    private Aircraft aircraft;
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    @ManyToOne
    @JoinColumn(name = "maintenance_provider_id")
    @JsonIgnoreProperties({"repairRequests", "specializations"})
    private MaintenanceProvider maintenanceProvider;
    
    public ServiceRequest() {
    }
    
    /**
     * UC HA.6: Im Diagramm "4: create(services, location, ac, hp, mp)"
     */
    public ServiceRequest(String services, String location, Aircraft ac, 
                         HangarProvider hp, MaintenanceProvider mp) {
        this.services = services;
        this.location = location;
        this.aircraft = ac;
        this.hangarProvider = hp;
        this.maintenanceProvider = mp;
    }
    
    /**
     * UC FB.4: Im Diagramm "3: create(duration, services, hp, ac)"
     */
    public ServiceRequest(String duration, String services, HangarProvider hp, Aircraft ac) {
        this.duration = duration;
        this.services = services;
        this.hangarProvider = hp;
        this.aircraft = ac;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getServices() {
        return services;
    }
    
    public void setServices(String services) {
        this.services = services;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Aircraft getAircraft() {
        return aircraft;
    }
    
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
    
    public HangarProvider getHangarProvider() {
        return hangarProvider;
    }
    
    public void setHangarProvider(HangarProvider hangarProvider) {
        this.hangarProvider = hangarProvider;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
        
    
    public MaintenanceProvider getMaintenanceProvider() {
        return maintenanceProvider;
    }
    
    public void setMaintenanceProvider(MaintenanceProvider maintenanceProvider) {
        this.maintenanceProvider = maintenanceProvider;
    }
}
