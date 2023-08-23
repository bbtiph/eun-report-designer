package org.eun.back.repository;

import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.domain.PersonEunIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonEunIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonEunIndicatorRepository extends JpaRepository<PersonEunIndicator, Long> {
    PersonEunIndicator findByCountryIdAndProjectIdAndReportsProjectId(Long countryId, Long projectId, Long reportsProjectId);
}
