package de.thm.mni.repository;

import de.thm.mni.model.PartsProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UC FB.10: PartsProviderRepository
 */
@Repository
public interface PartsProviderRepository extends JpaRepository<PartsProvider, Long> {
}
