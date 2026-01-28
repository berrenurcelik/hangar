package de.thm.mni.repository;

import de.thm.mni.model.HangarProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangarProviderRepository extends JpaRepository<HangarProvider, Long> {

    java.util.Optional<HangarProvider> findByEmail(String email);
}
