package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Integer price;
    
    @ElementCollection
    private Set<String> conditions = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "hangar_provider_id")
    @JsonIgnoreProperties({"parkings", "selectedAircrafts", "services", "repairRequests"})
    private HangarProvider hangarProvider;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_owner_id")
    @JsonIgnoreProperties({"aircrafts", "bookedServices"})
    private AircraftOwner aircraftOwner;
    
    // Constructors
    public Service() {
    }
    
    public Service(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    /**
     * UC HA.2: Konstruktor mit HangarProvider und Bedingungen
     */
    public Service(HangarProvider hp, String name, String description, Integer cost, Set<String> conditions) {
        this.hangarProvider = hp;
        this.name = name;
        this.description = description;
        this.price = cost;
        this.conditions = conditions != null ? conditions : new HashSet<>();
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public Set<String> getConditions() {
        return conditions;
    }
    
    public void setConditions(Set<String> conditions) {
        this.conditions = conditions;
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
}
