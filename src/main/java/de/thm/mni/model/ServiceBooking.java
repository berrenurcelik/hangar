package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * UC FB.8: ServiceBooking Model
 */
@Entity
public class ServiceBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;
    private String timeWindow;
    private String status;
    private Double cost;
    private String services;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_owner_id")
    @JsonIgnoreProperties({"aircrafts", "bookedServices"})
    private AircraftOwner aircraftOwner;
    
    public ServiceBooking() {
    }
    
    /**
     * UC FB.8: Im Diagramm "3: create(type, timeWindow, status, cost, aco, services)"
     */
    public ServiceBooking(String type, String timeWindow, String status, Double cost, 
                         AircraftOwner aco, String services) {
        this.type = type;
        this.timeWindow = timeWindow;
        this.status = status;
        this.cost = cost;
        this.aircraftOwner = aco;
        this.services = services;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getTimeWindow() {
        return timeWindow;
    }
    
    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Double getCost() {
        return cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public String getServices() {
        return services;
    }
    
    public void setServices(String services) {
        this.services = services;
    }
    
    public AircraftOwner getAircraftOwner() {
        return aircraftOwner;
    }
    
    public void setAircraftOwner(AircraftOwner aircraftOwner) {
        this.aircraftOwner = aircraftOwner;
    }
}
