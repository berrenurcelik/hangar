package de.thm.mni.service;

import de.thm.mni.model.ArticleReservation;
import de.thm.mni.repository.ArticleReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UC FB.10: ArticleReservationCatalog
 * Im Diagramm: "4.1: add(ar)"
 */
@Component
public class ArticleReservationCatalog {

    @Autowired
    private ArticleReservationRepository repository;

    /**
     * Im Diagramm: "4.1: add(ar)" Nachricht
     */
    public void add(ArticleReservation ar) {
        repository.save(ar);
    }
}
