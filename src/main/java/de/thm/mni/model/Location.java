package de.thm.mni.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UC FB.3: Location Model
 */
@Entity
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String location;
    
    @ElementCollection
    private Set<String> features = new HashSet<>();
    
    @ElementCollection
    private Set<String> services = new HashSet<>();
    
    @ElementCollection
    private Set<String> conditions = new HashSet<>();
    
    public Location() {
    }
    
    public Location(String location, Set<String> features, Set<String> services, Set<String> conditions) {
        this.location = location;
        this.features = features != null ? features : new HashSet<>();
        this.services = services != null ? services : new HashSet<>();
        this.conditions = conditions != null ? conditions : new HashSet<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Set<String> getFeatures() {
        return features;
    }
    
    public void setFeatures(Set<String> features) {
        this.features = features;
    }
    
    public Set<String> getServices() {
        return services;
    }
    
    public void setServices(Set<String> services) {
        this.services = services;
    }
    
    public Set<String> getConditions() {
        return conditions;
    }
    
    public void setConditions(Set<String> conditions) {
        this.conditions = conditions;
    }
}
