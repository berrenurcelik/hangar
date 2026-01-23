package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class RepairAppointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "repair_request_id")
    @JsonIgnoreProperties({"repairAppointments", "maintenanceProvider", "hangarProvider"})
    private RepairRequest repairRequest;
    
    // Constructors
    public RepairAppointment() {
    }
    
    public RepairAppointment(Date date, String status) {
        this.date = date;
        this.status = status;
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
    
    public RepairRequest getRepairRequest() {
        return repairRequest;
    }
    
    public void setRepairRequest(RepairRequest repairRequest) {
        this.repairRequest = repairRequest;
    }
}
