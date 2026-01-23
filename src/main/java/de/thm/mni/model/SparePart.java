package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class SparePart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    private Boolean availability;
    private Boolean isReserved;
    
    @ManyToOne
    @JoinColumn(name = "parts_provider_id")
    @JsonIgnoreProperties({"spareParts", "specializations"})
    private PartsProvider partsProvider;
    
    // Constructors
    public SparePart() {
    }
    
    public SparePart(String name, String description, Integer price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.availability = quantity > 0;
        this.isReserved = false;
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
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.availability = quantity > 0;
    }
    
    public Boolean getAvailability() {
        return availability;
    }
    
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
    
    public Boolean getIsReserved() {
        return isReserved;
    }
    
    public void setIsReserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }
    
    public PartsProvider getPartsProvider() {
        return partsProvider;
    }
    
    public void setPartsProvider(PartsProvider partsProvider) {
        this.partsProvider = partsProvider;
    }
}
