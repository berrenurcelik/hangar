package de.thm.mni.repository;

import de.thm.mni.model.ArticleReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UC FB.10: ArticleReservationRepository
 */
@Repository
public interface ArticleReservationRepository extends JpaRepository<ArticleReservation, Long> {
}
