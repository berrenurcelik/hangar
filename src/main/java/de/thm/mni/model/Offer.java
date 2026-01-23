package de.thm.mni.model;

import jakarta.persistence.*;

/**
 * UC FB.5: Offer Model
 */
@Entity
public class Offer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String status;
    
    public Offer() {
    }
    
    /**
     * UC FB.5: Im Diagramm "2: set(status)"
     */
    public void set(String status) {
        this.status = status;
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
}
