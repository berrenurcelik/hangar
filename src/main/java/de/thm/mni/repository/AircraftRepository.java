package de.thm.mni.repository;

import de.thm.mni.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    /** UC FB.4: Flugzeuge eines Flugzeugbesitzers für Serviceanfrage-Dropdown */
    List<Aircraft> findByAircraftOwner_Id(Long ownerId);
}
