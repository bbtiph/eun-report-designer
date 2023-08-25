package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.OrganizationEunIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationEunIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationEunIndicatorRepository extends JpaRepository<OrganizationEunIndicator, Long> {
    OrganizationEunIndicator findByCountryIdAndProjectIdAndReportsProjectId(Long countryId, Long projectId, Long reportsProjectId);

    List<OrganizationEunIndicator> findAllByCountryNameEquals(String countryName);
}
