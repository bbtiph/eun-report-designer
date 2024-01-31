package org.eun.back.repository;

import org.eun.back.domain.ProjectPartner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectPartnerRepository extends JpaRepository<ProjectPartner, Long> {}
