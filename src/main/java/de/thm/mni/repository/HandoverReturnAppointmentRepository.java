package de.thm.mni.repository;

import de.thm.mni.model.HandoverReturnAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UC FB.7: HandoverReturnAppointmentRepository
 */
@Repository
public interface HandoverReturnAppointmentRepository extends JpaRepository<HandoverReturnAppointment, Long> {
}
