package org.eun.back.repository;

import org.eun.back.domain.MoeContacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MoeContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoeContactsRepository extends JpaRepository<MoeContacts, Long> {}
