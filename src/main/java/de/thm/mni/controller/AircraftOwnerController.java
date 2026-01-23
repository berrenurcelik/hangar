package de.thm.mni.controller;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.repository.AircraftOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft-owners")
@CrossOrigin(origins = "*")
public class AircraftOwnerController {
    
    @Autowired
    private AircraftOwnerRepository ownerRepository;
    
    @GetMapping
    public List<AircraftOwner> getAllOwners() {
        return ownerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AircraftOwner> getOwnerById(@PathVariable Long id) {
        return ownerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public AircraftOwner createOwner(@RequestBody AircraftOwner owner) {
        return ownerRepository.save(owner);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AircraftOwner> updateOwner(@PathVariable Long id, @RequestBody AircraftOwner ownerDetails) {
        return ownerRepository.findById(id)
                .map(owner -> {
                    owner.setName(ownerDetails.getName());
                    owner.setEmail(ownerDetails.getEmail());
                    owner.setContact(ownerDetails.getContact());
                    return ResponseEntity.ok(ownerRepository.save(owner));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        return ownerRepository.findById(id)
                .map(owner -> {
                    ownerRepository.delete(owner);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
