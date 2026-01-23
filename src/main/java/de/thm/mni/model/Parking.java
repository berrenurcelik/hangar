package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Parking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String status;
    private Integer number;
    
    @ElementCollection
    private Set<String> locationFeatures = new HashSet<>();
    
    private String siteStatus;
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private Set<AircraftAppointment> aircraftAppointments = new HashSet<>();
    
    // Constructors
    public Parking() {
    }
    
    public Parking(String status, Integer number, String siteStatus) {
        this.status = status;
        this.number = number;
        this.siteStatus = siteStatus;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public Set<String> getLocationFeatures() {
        return locationFeatures;
    }
    
    public void setLocationFeatures(Set<String> locationFeatures) {
        this.locationFeatures = locationFeatures;
    }
    
    public String getSiteStatus() {
        return siteStatus;
    }
    
    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }
    
    public HangarProvider getHangarProvider() {
        return hangarProvider;
    }
    
    public void setHangarProvider(HangarProvider hangarProvider) {
        this.hangarProvider = hangarProvider;
    }
    
    public Set<AircraftAppointment> getAircraftAppointments() {
        return aircraftAppointments;
    }
    
    public void setAircraftAppointments(Set<AircraftAppointment> aircraftAppointments) {
        this.aircraftAppointments = aircraftAppointments;
    }
}
