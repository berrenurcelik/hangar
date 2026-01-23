package de.thm.mni.model;

import jakarta.persistence.*;

/**
 * UC HA.5: MaintenanceStatus Model
 */
@Entity
public class MaintenanceStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String maintenance;
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    private HangarProvider hangarProvider;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
    
    public MaintenanceStatus() {
    }
    
    /**
     * UC HA.5: Im Diagramm "3: create(maintenance, hp, ac)"
     */
    public MaintenanceStatus(String maintenance, HangarProvider hp, Aircraft ac) {
        this.maintenance = maintenance;
        this.hangarProvider = hp;
        this.aircraft = ac;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMaintenance() {
        return maintenance;
    }
    
    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }
    
    public HangarProvider getHangarProvider() {
        return hangarProvider;
    }
    
    public void setHangarProvider(HangarProvider hangarProvider) {
        this.hangarProvider = hangarProvider;
    }
    
    public Aircraft getAircraft() {
        return aircraft;
    }
    
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}
