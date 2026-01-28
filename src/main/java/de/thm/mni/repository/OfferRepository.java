package de.thm.mni.repository;

import de.thm.mni.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UC FB.5: OfferRepository
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    /** UC FB.5: Meine Angebote – alle Angebote zu Serviceanfragen des Flugzeugbesitzers */
    List<Offer> findByServiceRequest_Aircraft_AircraftOwner_Id(Long ownerId);
}
