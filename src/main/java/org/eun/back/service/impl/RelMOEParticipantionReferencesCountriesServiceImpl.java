package org.eun.back.service.impl;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.RelMOEParticipationReferencesCountries;
import org.eun.back.domain.RelMOEParticipationReferencesCountries;
import org.eun.back.repository.RelEventReferencesCountriesRepository;
import org.eun.back.repository.RelMOEParticipantionReferencesCountriesRepository;
import org.eun.back.service.RelMOEParticipantionReferencesCountriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelMOEParticipantionReferencesCountriesServiceImpl implements RelMOEParticipantionReferencesCountriesService {

    private final Logger log = LoggerFactory.getLogger(RelMOEParticipantionReferencesCountriesServiceImpl.class);

    private final RelMOEParticipantionReferencesCountriesRepository relMOEParticipantionReferencesCountriesRepository;

    public RelMOEParticipantionReferencesCountriesServiceImpl(
        RelMOEParticipantionReferencesCountriesRepository relMOEParticipantionReferencesCountriesRepository
    ) {
        this.relMOEParticipantionReferencesCountriesRepository = relMOEParticipantionReferencesCountriesRepository;
    }

    @Override
    public List<RelMOEParticipationReferencesCountries> findAll() {
        return relMOEParticipantionReferencesCountriesRepository.findAll();
    }

    @Override
    public RelMOEParticipationReferencesCountries save(RelMOEParticipationReferencesCountries relEventReferencesCountries) {
        return relMOEParticipantionReferencesCountriesRepository.save(relEventReferencesCountries);
    }

    @Override
    public RelMOEParticipationReferencesCountries update(RelMOEParticipationReferencesCountries relEventReferencesCountries) {
        return relMOEParticipantionReferencesCountriesRepository.save(relEventReferencesCountries);
    }

    @Override
    public void delete(Long id) {
        relMOEParticipantionReferencesCountriesRepository.deleteById(id);
    }

    @Override
    public Optional<RelMOEParticipationReferencesCountries> findFirstByCountriesIdAndMoeParticipationReferencesId(
        Long countriesId,
        Long referencesId
    ) {
        return relMOEParticipantionReferencesCountriesRepository.findFirstByCountriesIdAndMoeParticipationReferencesId(
            countriesId,
            referencesId
        );
    }

    @Override
    public List<RelMOEParticipationReferencesCountries> findFirstByCountriesId(Long countriesId) {
        return relMOEParticipantionReferencesCountriesRepository.findFirstByCountriesId(countriesId);
    }

    @Override
    public List<RelMOEParticipationReferencesCountries> findFirstByReferencesId(Long referencesId) {
        return relMOEParticipantionReferencesCountriesRepository.findAllByMoeParticipationReferencesId(referencesId);
    }
}
