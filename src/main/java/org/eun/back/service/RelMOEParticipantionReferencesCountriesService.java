package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.RelMOEParticipationReferencesCountries;

public interface RelMOEParticipantionReferencesCountriesService {
    List<RelMOEParticipationReferencesCountries> findAll();

    RelMOEParticipationReferencesCountries save(RelMOEParticipationReferencesCountries relEventReferencesCountries);

    RelMOEParticipationReferencesCountries update(RelMOEParticipationReferencesCountries relEventReferencesCountries);

    void delete(Long id);

    Optional<RelMOEParticipationReferencesCountries> findFirstByCountriesIdAndMoeParticipationReferencesId(
        Long countriesId,
        Long referencesId
    );

    List<RelMOEParticipationReferencesCountries> findFirstByCountriesId(Long countriesId);

    List<RelMOEParticipationReferencesCountries> findFirstByReferencesId(Long referencesId);
}
