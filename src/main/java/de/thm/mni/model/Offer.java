package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * UC FB.5: Offer Model – Angebot des Hangaranbieters auf eine Serviceanfrage (FB.4)
 * Ein Angebot bezieht sich auf einen ServiceRequest und enthält Preis sowie Status (PENDING/ACCEPTED/REJECTED).
 */
@Entity
public class Offer {
    
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** PENDING, ACCEPTED, REJECTED */
    private String status;
    
    /** Angebotspreis in Euro (z. B. für Dauer, Kosten, Leistungen) */
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "service_request_id")
    @JsonIgnoreProperties({"aircraft", "maintenanceProvider"})
    private ServiceRequest serviceRequest;
    
    public Offer() {
    }
    
    public Offer(ServiceRequest serviceRequest, Double price, String status) {
        this.serviceRequest = serviceRequest;
        this.price = price;
        this.status = status != null ? status : STATUS_PENDING;
    }
    
    /**
     * UC FB.5: Im Diagramm "2: set(status)" – Entscheidung annehmen/ablehnen
     */
    public void set(String status) {
        this.status = status;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public ServiceRequest getServiceRequest() { return serviceRequest; }
    public void setServiceRequest(ServiceRequest serviceRequest) { this.serviceRequest = serviceRequest; }
}
