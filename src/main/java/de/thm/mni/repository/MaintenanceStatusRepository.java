package de.thm.mni.repository;

import de.thm.mni.model.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceStatusRepository extends JpaRepository<MaintenanceStatus, Long> {
}
