package de.thm.mni.repository;

import de.thm.mni.model.MaintenanceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceProviderRepository extends JpaRepository<MaintenanceProvider, Long> {
}
