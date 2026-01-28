package de.thm.mni.service;

import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.ArticleReservation;
import de.thm.mni.model.PartsProvider;
import de.thm.mni.model.SparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UC FB.10: ArticleReservationService
 */
@Service
public class ArticleReservationService {

    @Autowired
    private ACOCatalog acoCatalog;
    
    @Autowired
    private PSCatalog psCatalog;
    
    @Autowired
    private PartsCatalog partsCatalog;
    
    @Autowired
    private ArticleReservationCatalog articleReservationCatalog;

    /** FB.10 5a/5a1: Kennung, wenn Artikel nicht verfügbar */
    public static final String ERROR_ARTIKEL_NICHT_VERFUEGBAR = "ARTIKEL_NICHT_VERFUEGBAR";

    /**
     * UC FB.10 / StR.EA.3: createArticleReservation
     * 
     * Im Diagramm:
     * 1: aco := getACO(ACOId) -> ACOCatalog
     * 2: ps := getPS(PSId) -> PSCatalog
     * 3: sp := getSparePart(sparePartID) -> SparePartCatalog
     * FB:10 - Schritt 5/5a: Verfügbarkeit prüfen; bei 5a RuntimeException(ERROR_ARTIKEL_NICHT_VERFUEGBAR)
     * 4: create(sp, quantity, status, aco, ps) -> ar:ArticleReservation
     * 4.1: add(ar) -> ArticleReservationCatalog
     * FB.10 Schritt 6: Artikelbestand anpassen
     */
    @Transactional
    public ArticleReservation createArticleReservation(Long acoId, Long psId, Long sparePartID, 
                                                      Integer quantity, String status) {
        
        // 1: AircraftOwner aus ACOCatalog holen
        AircraftOwner aco = acoCatalog.getACO(acoId);
        
        // 2: PartsProvider aus PSCatalog holen
        PartsProvider ps = psCatalog.getPS(psId);
        
        // 3: SparePart aus SparePartCatalog holen
        SparePart sp = partsCatalog.getSparePart(sparePartID);
        
        // FB.10 Schritt 5 / 5a: Verfügbarkeit prüfen
        int available = sp.getQuantity() != null ? sp.getQuantity() : 0;
        if (available < quantity || !Boolean.TRUE.equals(sp.getAvailability())) {
            throw new RuntimeException(ERROR_ARTIKEL_NICHT_VERFUEGBAR);
        }
        
        // 4: ArticleReservation erstellen
        ArticleReservation ar = new ArticleReservation(sp, quantity, status, aco, ps);
        
        // 4.1: Zum ArticleReservationCatalog hinzufügen
        articleReservationCatalog.add(ar);
        
        // FB.10 Schritt 6: Artikelbestand anpassen
        int newQty = available - quantity;
        sp.setQuantity(newQty);
        if (newQty <= 0) {
            sp.setAvailability(false);
        }
        partsCatalog.update(sp);
        
        return ar;
    }
}
