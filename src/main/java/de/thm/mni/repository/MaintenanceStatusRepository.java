package de.thm.mni.repository;

import de.thm.mni.model.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceStatusRepository extends JpaRepository<MaintenanceStatus, Long> {

    /** HA.5: aktueller Wartungsstatus für ein Flugzeug (letzter Eintrag) */
    Optional<MaintenanceStatus> findTopByAircraft_IdOrderByIdDesc(Long aircraftId);
}
