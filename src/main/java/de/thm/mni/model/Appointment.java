package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;

/**
 * UC HA.7: Appointment Model
 */
@Entity
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "service_request_id")
    @JsonIgnoreProperties({"appointment", "aircraftOwner", "service", "hangarProvider"})
    private ServiceRequest serviceRequest;
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    @ManyToOne
    @JoinColumn(name = "maintenance_provider_id")
    @JsonIgnoreProperties({"repairRequests", "specializations"})
    private MaintenanceProvider maintenanceProvider;
    
    public Appointment() {
    }
    
    /**
     * UC HA.7: Im Diagramm "4: create(date, status, sr, hp, mp)"
     */
    public Appointment(Date date, String status, ServiceRequest sr, 
                      HangarProvider hp, MaintenanceProvider mp) {
        this.date = date;
        this.status = status;
        this.serviceRequest = sr;
        this.hangarProvider = hp;
        this.maintenanceProvider = mp;
    }
    
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
    
    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }
    
    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }
    
    public HangarProvider getHangarProvider() {
        return hangarProvider;
    }
    
    public void setHangarProvider(HangarProvider hangarProvider) {
        this.hangarProvider = hangarProvider;
    }
    
    public MaintenanceProvider getMaintenanceProvider() {
        return maintenanceProvider;
    }
    
    public void setMaintenanceProvider(MaintenanceProvider maintenanceProvider) {
        this.maintenanceProvider = maintenanceProvider;
    }
}
