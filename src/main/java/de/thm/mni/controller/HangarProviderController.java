package de.thm.mni.controller;

import de.thm.mni.model.HangarProvider;
import de.thm.mni.service.HPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hangar-providers")
@CrossOrigin(origins = "*")
public class HangarProviderController {
    
    @Autowired
    private HPService hpService;
    
    @GetMapping
    public List<HangarProvider> getAllProviders() {
        return hpService.getAllProfiles();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HangarProvider> getProviderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(hpService.getProfile(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * UC HA.1: saveProfile Endpunkt
     */
    @PostMapping
    public ResponseEntity<HangarProvider> createProvider(@RequestBody HangarProvider provider) {
        try {
            HangarProvider newProvider = hpService.saveProfile(
                provider.getName(),
                provider.getEmail(),
                provider.getPassword(),
                provider.getContact(),
                provider.getServiceHours(),
                provider.getStorageConditions()
            );
            return ResponseEntity.ok(newProvider);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
   
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvider(@PathVariable Long id) {
        try {
            HangarProvider provider = hpService.getProfile(id);
            hpService.deleteProfile(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
