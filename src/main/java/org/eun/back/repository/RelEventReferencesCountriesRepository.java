package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.RelEventReferencesCountries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelEventReferencesCountriesRepository extends JpaRepository<RelEventReferencesCountries, Long> {
    Optional<RelEventReferencesCountries> findFirstByCountriesIdAndEventReferencesId(Long countriesId, Long referencesId);
}
