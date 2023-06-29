package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.MoeContacts;
import org.eun.back.service.dto.MoeContactsDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MoeContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoeContactsRepository extends JpaRepository<MoeContacts, Long> {
    MoeContacts findByCountryCodeAndMinistryEnglishName(String countryCode, String ministryName);

    @Modifying
    @Query("UPDATE MoeContacts e SET e.isActive = false")
    void updateAllIsActiveToFalse();
}
