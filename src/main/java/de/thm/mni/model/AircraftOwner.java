package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AircraftOwner extends Benutzer {
    
    @OneToMany(mappedBy = "aircraftOwner", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"aircraftOwner", "aircraftAppointments"})
    private Set<Aircraft> aircrafts = new HashSet<>();
    
    @OneToMany(mappedBy = "aircraftOwner", cascade = CascadeType.ALL)
    private Set<Service> bookedServices = new HashSet<>();
    
    // Constructors
    public AircraftOwner() {
        super();
    }
    
    public AircraftOwner(String name, String email, String password, String contact) {
        super(name, email, password, contact, Role.AIRCRAFT_OWNER);
    }
    
    // Business Methods
    public void saveProfile() {
        // Implementation
    }
    
    public void searchParking() {
        // Implementation
    }
    
    public void sendInquiry() {
        // Implementation
    }
    
    public void requestLocationInfo() {
        // Implementation
    }
    
    public void requestOffer() {
        // Implementation
    }
    
    public void acceptOffer() {
        // Implementation
    }
    
    public void rejectOffer() {
        // Implementation
    }
    
    public void scheduleAircraftAppointment() {
        // Implementation
    }
    
    public void bookService() {
        // Implementation
    }
    
    public void reserveSparePartQuantity(Integer quantity) {
        // Implementation
    }
    
    // Getters and Setters
    public Set<Aircraft> getAircrafts() {
        return aircrafts;
    }
    
    public void setAircrafts(Set<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }
    
    public Set<Service> getBookedServices() {
        return bookedServices;
    }
    
    public void setBookedServices(Set<Service> bookedServices) {
        this.bookedServices = bookedServices;
    }
}
