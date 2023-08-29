package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.domain.ParticipantsEunIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ParticipantsEunIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantsEunIndicatorRepository extends JpaRepository<ParticipantsEunIndicator, Long> {
    ParticipantsEunIndicator findByCountryCodeAndCourseId(String countryCode, String courseId);
    List<ParticipantsEunIndicator> findAllByCountryCodeEquals(String countryCode);
}
