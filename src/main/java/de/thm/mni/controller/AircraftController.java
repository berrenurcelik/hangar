package de.thm.mni.controller;

import de.thm.mni.model.Aircraft;
import de.thm.mni.repository.AircraftRepository;
import de.thm.mni.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
@CrossOrigin(origins = "*")
public class AircraftController {
    
    @Autowired
    private AircraftRepository aircraftRepository;
    
    @Autowired
    private AircraftService aircraftService;
    
    @GetMapping
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraftDetails) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraft.setSize(aircraftDetails.getSize());
                    aircraft.setMaintenanceStatus(aircraftDetails.getMaintenanceStatus());
                    aircraft.setFlightReadiness(aircraftDetails.getFlightReadiness());
                    aircraft.setRegistrationMark(aircraftDetails.getRegistrationMark());
                    aircraft.setDimensions(aircraftDetails.getDimensions());
                    return ResponseEntity.ok(aircraftRepository.save(aircraft));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAircraft(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraftRepository.delete(aircraft);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * UC FB.9: saveAircraft Endpunkt
     * 
     * Im Diagramm: saveAircraft(aircraftID, dimensions, registrationMark, image)
     */
    @PostMapping("/save")
    public ResponseEntity<Aircraft> saveAircraft(
            @RequestParam(required = false) Long aircraftId,
            @RequestParam Long acoId,
            @RequestParam String dimensions,
            @RequestParam String registrationMark,
            @RequestParam(required = false) MultipartFile image) {
        
        try {
            // MultipartFile zu byte[] konvertieren
            byte[] imageBytes = null;
            if (image != null && !image.isEmpty()) {
                imageBytes = image.getBytes();
            }
            
            // Service aufrufen
            Aircraft aircraft = aircraftService.saveAircraft(
                aircraftId, 
                acoId, 
                dimensions, 
                registrationMark, 
                imageBytes
            );
            
            return ResponseEntity.ok(aircraft);
            
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
