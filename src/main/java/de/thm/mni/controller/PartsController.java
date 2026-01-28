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
     * UC FB.6: searchParts – Suchkriterien, durchsucht Anbieterbestände.
     * Bei keiner Treffer: leere Liste (5a1), Frontend zeigt „keine Ergebnisse“.
     */
    @GetMapping("/search")
    public ResponseEntity<List<SparePart>> searchParts(@RequestParam(required = false) String criteria) {
        try {
            List<SparePart> parts = partsManager.searchParts(criteria != null ? criteria : "");
            return ResponseEntity.ok(parts);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * StR.E.6 Schritt 7: Detailinformationen des Artikels
     */
    @GetMapping("/{id}")
    public ResponseEntity<SparePart> getPartById(@PathVariable Long id) {
        try {
            SparePart part = partsManager.getPartById(id);
            return ResponseEntity.ok(part);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
