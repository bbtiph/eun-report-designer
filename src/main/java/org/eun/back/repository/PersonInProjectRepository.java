package org.eun.back.repository;

import org.eun.back.domain.PersonInProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonInProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInProjectRepository extends JpaRepository<PersonInProject, Long> {}
