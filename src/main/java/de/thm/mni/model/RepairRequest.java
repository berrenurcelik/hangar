package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RepairRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ElementCollection
    private Set<String> services = new HashSet<>();
    
    @ElementCollection
    private Set<String> parts = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "maintenance_provider_id")
    @JsonIgnoreProperties({"repairRequests", "specializations"})
    private MaintenanceProvider maintenanceProvider;
    
    @OneToMany(mappedBy = "repairRequest", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"repairRequest"})
    private Set<RepairAppointment> repairAppointments = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    // Constructors
    public RepairRequest() {
    }
    
    public RepairRequest(Set<String> services, Set<String> parts) {
        this.services = services;
        this.parts = parts;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Set<String> getServices() {
        return services;
    }
    
    public void setServices(Set<String> services) {
        this.services = services;
    }
    
    public Set<String> getParts() {
        return parts;
    }
    
    public void setParts(Set<String> parts) {
        this.parts = parts;
    }
    
    public MaintenanceProvider getMaintenanceProvider() {
        return maintenanceProvider;
    }
    
    public void setMaintenanceProvider(MaintenanceProvider maintenanceProvider) {
        this.maintenanceProvider = maintenanceProvider;
    }
    
    public Set<RepairAppointment> getRepairAppointments() {
        return repairAppointments;
    }
    
    public void setRepairAppointments(Set<RepairAppointment> repairAppointments) {
        this.repairAppointments = repairAppointments;
    }
    
    public HangarProvider getHangarProvider() {
        return hangarProvider;
    }
    
    public void setHangarProvider(HangarProvider hangarProvider) {
        this.hangarProvider = hangarProvider;
    }
}
