package de.thm.mni.controller;

import de.thm.mni.model.ArticleReservation;
import de.thm.mni.service.ArticleReservationService;
import de.thm.mni.repository.ArticleReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/article-reservation")
@CrossOrigin(origins = "*")
public class ArticleReservationController {
    
    @Autowired
    private ArticleReservationService articleReservationService;
    
    @Autowired
    private ArticleReservationRepository articleReservationRepository;
    
    @GetMapping
    public ResponseEntity<List<ArticleReservation>> getAllReservations() {
        try {
            List<ArticleReservation> reservations = articleReservationRepository.findAll();
            return ResponseEntity.ok(reservations);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            articleReservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * UC FB.10: createArticleReservation Endpunkt
     */
    @PostMapping("/create")
    public ResponseEntity<ArticleReservation> createArticleReservation(@RequestBody Map<String, Object> request) {
        try {
            Long acoId = Long.valueOf(request.get("acoId").toString());
            Long psId = Long.valueOf(request.get("psId").toString());
            Long sparePartID = Long.valueOf(request.get("sparePartID").toString());
            Integer quantity = Integer.valueOf(request.get("quantity").toString());
            String status = request.get("status").toString();
            
            ArticleReservation ar = articleReservationService.createArticleReservation(
                acoId, psId, sparePartID, quantity, status
            );
            
            return ResponseEntity.ok(ar);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
