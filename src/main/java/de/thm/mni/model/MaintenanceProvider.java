package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MaintenanceProvider extends Benutzer {
    
    @ElementCollection
    private Set<String> specializations = new HashSet<>();
    
    private String manufacturer;
    
    @OneToMany(mappedBy = "maintenanceProvider", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"maintenanceProvider", "hangarProvider", "repairAppointments"})
    private Set<RepairRequest> repairRequests = new HashSet<>();
    
    // Constructors
    public MaintenanceProvider() {
        super();
    }
    
    public MaintenanceProvider(String name, String email, String password, String contact, String manufacturer) {
        super(name, email, password, contact, Role.MAINTENANCE_PROVIDER);
        this.manufacturer = manufacturer;
    }
    
    // Business Methods
    public void saveProfile() {
        // Implementation
    }
    
    public void confirmRepairRequest() {
        // Implementation
    }
    
    // Getters and Setters
    public Set<String> getSpecializations() {
        return specializations;
    }
    
    public void setSpecializations(Set<String> specializations) {
        this.specializations = specializations;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public Set<RepairRequest> getRepairRequests() {
        return repairRequests;
    }
    
    public void setRepairRequests(Set<RepairRequest> repairRequests) {
        this.repairRequests = repairRequests;
    }
}
