package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * UC HA.5: FlightReadiness Model
 */
@Entity
public class FlightReadiness {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String flightReadiness;
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    @JsonIgnoreProperties({"aircraftAppointments", "aircraftOwner"})
    private Aircraft aircraft;
    
    public FlightReadiness() {
    }
    
    /**
     * UC HA.5: Im Diagramm "4: create(flightReadiness, hp, ac)"
     */
    public FlightReadiness(String flightReadiness, HangarProvider hp, Aircraft ac) {
        this.flightReadiness = flightReadiness;
        this.hangarProvider = hp;
        this.aircraft = ac;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFlightReadiness() {
        return flightReadiness;
    }
    
    public void setFlightReadiness(String flightReadiness) {
        this.flightReadiness = flightReadiness;
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
