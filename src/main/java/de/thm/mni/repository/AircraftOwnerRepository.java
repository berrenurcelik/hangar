package de.thm.mni.repository;

import de.thm.mni.model.AircraftOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftOwnerRepository extends JpaRepository<AircraftOwner, Long> {

    java.util.Optional<AircraftOwner> findByEmail(String email);
}
