package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"aircraftAppointments"})
public class Aircraft {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer size;
    private String maintenanceStatus;
    private String flightReadiness;
    private String registrationMark;
    private String dimensions;
    
    @Lob
    private byte[] photo;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_owner_id")
    @JsonIgnoreProperties({"aircrafts", "bookedServices"})
    private AircraftOwner aircraftOwner;
    
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private Set<AircraftAppointment> aircraftAppointments = new HashSet<>();
    
    public Aircraft() {
    }
    
    /**
     * UC FB.9: Im Diagramm "3a: create(aircraftID, dimensions, registrationMark, image, aco)"
     */
    public Aircraft(String dimensions, String registrationMark, byte[] image, AircraftOwner aco) {
        this.dimensions = dimensions;
        this.registrationMark = registrationMark;
        this.photo = image;
        this.aircraftOwner = aco;
    }
    
    /**
     * UC FB.9: Im Diagramm "3b: update(dimensions, registrationMark, image)"
     */
    public void update(String dimensions, String registrationMark, byte[] image) {
        this.dimensions = dimensions;
        this.registrationMark = registrationMark;
        this.photo = image;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }
    
    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }
    
    public String getFlightReadiness() {
        return flightReadiness;
    }
    
    public void setFlightReadiness(String flightReadiness) {
        this.flightReadiness = flightReadiness;
    }
    
    public String getRegistrationMark() {
        return registrationMark;
    }
    
    public void setRegistrationMark(String registrationMark) {
        this.registrationMark = registrationMark;
    }
    
    public String getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    
    public byte[] getPhoto() {
        return photo;
    }
    
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    public AircraftOwner getAircraftOwner() {
        return aircraftOwner;
    }
    
    public void setAircraftOwner(AircraftOwner aircraftOwner) {
        this.aircraftOwner = aircraftOwner;
    }
    
    public Set<AircraftAppointment> getAircraftAppointments() {
        return aircraftAppointments;
    }
    
    public void setAircraftAppointments(Set<AircraftAppointment> aircraftAppointments) {
        this.aircraftAppointments = aircraftAppointments;
    }
}
