package de.thm.mni.controller;

import de.thm.mni.model.SparePart;
import de.thm.mni.service.PartsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
@CrossOrigin(origins = "*")
public class PartsController {
    
    @Autowired
    private PartsManager partsManager;
    
    @GetMapping
    public ResponseEntity<List<SparePart>> getAllParts() {
        try {
            List<SparePart> parts = partsManager.getAllParts();
            return ResponseEntity.ok(parts);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * UC FB.6: searchParts Endpunkt
     */
    @GetMapping("/search")
    public ResponseEntity<List<SparePart>> searchParts(@RequestParam String criteria) {
        try {
            List<SparePart> parts = partsManager.searchParts(criteria);
            return ResponseEntity.ok(parts);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
