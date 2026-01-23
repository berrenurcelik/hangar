package de.thm.mni.repository;

import de.thm.mni.model.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UC FB.6: SparePartRepository
 */
@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Long> {
    List<SparePart> findByNameContainingOrDescriptionContaining(String name, String description);
}
