package de.thm.mni.repository;

import de.thm.mni.model.AircraftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftTypeRepository extends JpaRepository<AircraftType, Long> {
    java.util.Optional<AircraftType> findByNameIgnoreCase(String name);
}
