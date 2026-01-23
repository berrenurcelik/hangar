package de.thm.mni.repository;

import de.thm.mni.model.FlightReadiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightReadinessRepository extends JpaRepository<FlightReadiness, Long> {
}
