package de.thm.mni.repository;

import de.thm.mni.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Liefert eine Beispiel-Location für eine Stadt (z.B. "Frankfurt").
     * Wird verwendet, um neu angelegte Stellplätze automatisch mit
     * konsistenten Standortdetails zu verknüpfen.
     */
    java.util.Optional<Location> findFirstByLocationContainingIgnoreCase(String city);
}
