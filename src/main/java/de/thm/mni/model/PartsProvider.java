package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PartsProvider extends Benutzer {
    
    @ElementCollection
    private Set<String> specializations = new HashSet<>();
    
    @OneToMany(mappedBy = "partsProvider", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"partsProvider"})
    private Set<SparePart> spareParts = new HashSet<>();
    
    // Constructors
    public PartsProvider() {
        super();
    }
    
    public PartsProvider(String name, String email, String password, String contact) {
        super(name, email, password, contact, Role.PARTS_PROVIDER);
    }
    
    // Business Methods
    public void saveProfile() {
        // Implementation
    }
    
    public void saveSparePart() {
        // Implementation
    }
    
    public void removeSparePart() {
        // Implementation
    }
    
    // Getters and Setters
    public Set<String> getSpecializations() {
        return specializations;
    }
    
    public void setSpecializations(Set<String> specializations) {
        this.specializations = specializations;
    }
    
    public Set<SparePart> getSpareParts() {
        return spareParts;
    }
    
    public void setSpareParts(Set<SparePart> spareParts) {
        this.spareParts = spareParts;
    }
}
