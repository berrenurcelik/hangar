package de.thm.mni.repository;

import de.thm.mni.model.FlightReadiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightReadinessRepository extends JpaRepository<FlightReadiness, Long> {

    /** aktuelle Flugbereitschaft für ein Flugzeug */
    Optional<FlightReadiness> findTopByAircraft_IdOrderByIdDesc(Long aircraftId);
}
