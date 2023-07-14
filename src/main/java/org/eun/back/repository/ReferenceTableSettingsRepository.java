package org.eun.back.repository;

import org.eun.back.domain.ReferenceTableSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReferenceTableSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferenceTableSettingsRepository extends JpaRepository<ReferenceTableSettings, Long> {}
