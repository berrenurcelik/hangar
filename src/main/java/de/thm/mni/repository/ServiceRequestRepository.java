package de.thm.mni.repository;

import de.thm.mni.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    /** HA.7: offene/akzeptierte Anfragen eines Hangaranbieters (mit zugeordneter Werkstatt) */
    List<ServiceRequest> findByHangarProvider_IdAndMaintenanceProviderIsNotNull(Long hangarProviderId);
}
