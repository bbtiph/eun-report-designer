package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.RelMOEParticipationReferencesCountries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelMOEParticipantionReferencesCountriesRepository extends JpaRepository<RelMOEParticipationReferencesCountries, Long> {
    Optional<RelMOEParticipationReferencesCountries> findFirstByCountriesIdAndMoeParticipationReferencesId(
        Long countriesId,
        Long referencesId
    );

    List<RelMOEParticipationReferencesCountries> findFirstByCountriesId(Long countriesId);

    List<RelMOEParticipationReferencesCountries> findAllByMoeParticipationReferencesId(Long referencesId);
}
