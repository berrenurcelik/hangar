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

    /**
     * UC FB.10: createArticleReservation
     * 
     * Im Diagramm:
     * 1: aco := getACO(ACOId) -> ACOCatalog
     * 2: ps := getPS(PSId) -> PSCatalog
     * 3: sp := getSparePart(sparePartID) -> SparePartCatalog
     * 4: create(sp, quantity, status, aco, ps) -> ar:ArticleReservation
     * 4.1: add(ar) -> ArticleReservationCatalog
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
        
        // 4: ArticleReservation erstellen
        ArticleReservation ar = new ArticleReservation(sp, quantity, status, aco, ps);
        
        // 4.1: Zum ArticleReservationCatalog hinzufügen
        articleReservationCatalog.add(ar);
        
        return ar;
    }
}
