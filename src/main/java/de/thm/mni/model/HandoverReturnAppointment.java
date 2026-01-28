package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;

/**
 * UC FB.7 / StR.E.7: HandoverReturnAppointment Model – Übergabe- oder Rückgabetermin
 */
@Entity
public class HandoverReturnAppointment {
    
    public static final String TYPE_HANDOVER = "HANDOVER";
    public static final String TYPE_RETURN = "RETURN";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_REJECTED = "REJECTED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Date date;
    private String status;
    /** HANDOVER = Übergabetermin, RETURN = Rückgabetermin */
    private String appointmentType;
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_owner_id")
    @JsonIgnoreProperties({"aircrafts", "bookedServices"})
    private AircraftOwner aircraftOwner;
    
    public HandoverReturnAppointment() {
    }
    
    /**
     * UC FB.7 / StR.E.7: create(date, status, type, hp, aco)
     */
    public HandoverReturnAppointment(Date date, String status, String appointmentType, HangarProvider hp, AircraftOwner aco) {
        this.date = date;
        this.status = status;
        this.appointmentType = appointmentType != null ? appointmentType : TYPE_HANDOVER;
        this.hangarProvider = hp;
        this.aircraftOwner = aco;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public HangarProvider getHangarProvider() {
        return hangarProvider;
    }
    
    public void setHangarProvider(HangarProvider hangarProvider) {
        this.hangarProvider = hangarProvider;
    }
    
    public AircraftOwner getAircraftOwner() {
        return aircraftOwner;
    }
    
    public void setAircraftOwner(AircraftOwner aircraftOwner) {
        this.aircraftOwner = aircraftOwner;
    }
    
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
}
