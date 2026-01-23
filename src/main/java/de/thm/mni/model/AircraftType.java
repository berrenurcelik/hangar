package de.thm.mni.model;

import jakarta.persistence.*;

/**
 * UC HA.4.2: AircraftType Model
 */
@Entity
public class AircraftType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String type;
    private String size;
    
    public AircraftType() {
    }
    
    /**
     * UC HA.4.2: Im Diagramm "1: create(name, type, size)"
     */
    public AircraftType(String name, String type, String size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
}
