package de.thm.mni.repository;

import de.thm.mni.model.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UC FB.8: ServiceBookingRepository
 */
@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
}
