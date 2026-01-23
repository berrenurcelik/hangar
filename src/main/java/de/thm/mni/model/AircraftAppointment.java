package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class AircraftAppointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Temporal(TemporalType.TIME)
    private Date time;
    
    @Enumerated(EnumType.STRING)
    private AppointmentType typ;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    @JsonIgnoreProperties({"aircraftAppointments", "aircraftOwner"})
    private Aircraft aircraft;
    
    @ManyToOne
    @JoinColumn(name = "parking_id")
    @JsonIgnoreProperties({"aircraftAppointments", "hangarProvider"})
    private Parking parking;
    
    // Constructors
    public AircraftAppointment() {
    }
    
    public AircraftAppointment(Date date, Date time, AppointmentType typ) {
        this.date = date;
        this.time = time;
        this.typ = typ;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    public AppointmentType getTyp() {
        return typ;
    }
    
    public void setTyp(AppointmentType typ) {
        this.typ = typ;
    }
    
    public Aircraft getAircraft() {
        return aircraft;
    }
    
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
    
    public Parking getParking() {
        return parking;
    }
    
    public void setParking(Parking parking) {
        this.parking = parking;
    }
    
    // Enum for appointment type
    public enum AppointmentType {
        PARKING,
        MAINTENANCE,
        INSPECTION,
        REPAIR
    }
}
