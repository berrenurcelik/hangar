package de.thm.mni.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * UC FB.10: ArticleReservation Model
 */
@Entity
public class ArticleReservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantity;
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "spare_part_id")
    @JsonIgnoreProperties({"partsProvider"})
    private SparePart sparePart;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_owner_id")
    @JsonIgnoreProperties({"aircrafts", "bookedServices"})
    private AircraftOwner aircraftOwner;
    
    @ManyToOne
    @JoinColumn(name = "parts_provider_id")
    @JsonIgnoreProperties({"spareParts", "specializations"})
    private PartsProvider partsProvider;
    
    public ArticleReservation() {
    }
    
    /**
     * UC FB.10: Im Diagramm "4: create(sp, quantity, status, aco, ps)"
     */
    public ArticleReservation(SparePart sp, Integer quantity, String status, 
                             AircraftOwner aco, PartsProvider ps) {
        this.sparePart = sp;
        this.quantity = quantity;
        this.status = status;
        this.aircraftOwner = aco;
        this.partsProvider = ps;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public SparePart getSparePart() {
        return sparePart;
    }
    
    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }
    
    public AircraftOwner getAircraftOwner() {
        return aircraftOwner;
    }
    
    public void setAircraftOwner(AircraftOwner aircraftOwner) {
        this.aircraftOwner = aircraftOwner;
    }
    
    public PartsProvider getPartsProvider() {
        return partsProvider;
    }
    
    public void setPartsProvider(PartsProvider partsProvider) {
        this.partsProvider = partsProvider;
    }
}
