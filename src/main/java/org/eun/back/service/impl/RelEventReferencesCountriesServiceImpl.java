package org.eun.back.service.impl;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.RelEventReferencesCountries;
import org.eun.back.domain.RelReportBlocksReport;
import org.eun.back.repository.RelEventReferencesCountriesRepository;
import org.eun.back.repository.RelReportBlocksReportRepository;
import org.eun.back.service.RelEventReferencesCountriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelEventReferencesCountriesServiceImpl implements RelEventReferencesCountriesService {

    private final Logger log = LoggerFactory.getLogger(RelEventReferencesCountriesServiceImpl.class);

    private final RelEventReferencesCountriesRepository relEventReferencesCountriesRepository;

    public RelEventReferencesCountriesServiceImpl(RelEventReferencesCountriesRepository relEventReferencesCountriesRepository) {
        this.relEventReferencesCountriesRepository = relEventReferencesCountriesRepository;
    }

    @Override
    public List<RelEventReferencesCountries> findAll() {
        return relEventReferencesCountriesRepository.findAll();
    }

    @Override
    public RelEventReferencesCountries save(RelEventReferencesCountries relEventReferencesCountries) {
        return relEventReferencesCountriesRepository.save(relEventReferencesCountries);
    }

    @Override
    public RelEventReferencesCountries update(RelEventReferencesCountries relEventReferencesCountries) {
        return relEventReferencesCountriesRepository.save(relEventReferencesCountries);
    }

    @Override
    public void delete(Long id) {
        relEventReferencesCountriesRepository.deleteById(id);
    }

    @Override
    public Optional<RelEventReferencesCountries> findFirstByCountriesIdAndEventReferencesId(Long countriesId, Long referencesId) {
        return relEventReferencesCountriesRepository.findFirstByCountriesIdAndEventReferencesId(countriesId, referencesId);
    }
}
