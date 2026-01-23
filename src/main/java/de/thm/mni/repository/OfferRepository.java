package de.thm.mni.repository;

import de.thm.mni.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UC FB.5: OfferRepository
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
