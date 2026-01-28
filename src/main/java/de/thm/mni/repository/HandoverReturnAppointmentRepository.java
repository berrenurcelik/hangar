package de.thm.mni.repository;

import de.thm.mni.model.HandoverReturnAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UC FB.7: HandoverReturnAppointmentRepository
 */
@Repository
public interface HandoverReturnAppointmentRepository extends JpaRepository<HandoverReturnAppointment, Long> {

    List<HandoverReturnAppointment> findByAircraftOwner_Id(Long aircraftOwnerId);
    List<HandoverReturnAppointment> findByHangarProvider_Id(Long hangarProviderId);
    List<HandoverReturnAppointment> findByHangarProvider_IdAndStatus(Long hangarProviderId, String status);
}
