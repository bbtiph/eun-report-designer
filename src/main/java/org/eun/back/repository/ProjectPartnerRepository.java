package org.eun.back.repository;

import java.util.Optional;
import org.eun.back.domain.ProjectPartner;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectPartnerRepository extends JpaRepository<ProjectPartner, Long> {
    ProjectPartner findFirstByJobInfoIdAndCountryCodeAndVendorCode(Long jobInfoId, String countryCode, String vendorCode);
}
