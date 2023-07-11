package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.MoeContacts;
import org.eun.back.domain.WorkingGroupReferences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkingGroupReferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkingGroupReferencesRepository extends JpaRepository<WorkingGroupReferences, Long> {
    WorkingGroupReferences findByCountryCodeAndCountryRepresentativeMinistryAndType(String countryCode, String ministryName, String type);

    @Modifying
    @Query("UPDATE MoeContacts e SET e.isActive = false")
    void updateAllIsActiveToFalse();

    Page<WorkingGroupReferences> findAllByIsActive(Boolean isActive, Pageable pageable);

    List<WorkingGroupReferences> findAllByIsActiveAndCountryCode(Boolean isActive, String countryCode);
}
