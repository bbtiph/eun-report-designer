package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Countries;
import org.eun.back.domain.RelEventReferencesCountries;
import org.eun.back.service.dto.CountriesDTO;

public interface RelEventReferencesCountriesService {
    List<RelEventReferencesCountries> findAll();

    RelEventReferencesCountries save(RelEventReferencesCountries relEventReferencesCountries);

    RelEventReferencesCountries update(RelEventReferencesCountries relEventReferencesCountries);

    void delete(Long id);

    Optional<RelEventReferencesCountries> findFirstByCountriesIdAndEventReferencesId(Long countriesId, Long referencesId);

    List<RelEventReferencesCountries> findFirstByCountriesId(Long countriesId);

    List<RelEventReferencesCountries> findFirstByReferencesId(Long referencesId);
}
