package org.eun.back.service.impl;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.domain.ParticipantsEunIndicator;
import org.eun.back.repository.ParticipantsEunIndicatorRepository;
import org.eun.back.service.CountriesService;
import org.eun.back.service.ParticipantsEunIndicatorService;
import org.eun.back.service.dto.*;
import org.eun.back.service.mapper.ParticipantsEunIndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link ParticipantsEunIndicator}.
 */
@Service
@Transactional
public class ParticipantsEunIndicatorServiceImpl implements ParticipantsEunIndicatorService {

    @Value("${eun.reports-etl.url}")
    private String url;

    private final Logger log = LoggerFactory.getLogger(ParticipantsEunIndicatorServiceImpl.class);

    private final ParticipantsEunIndicatorRepository participantsEunIndicatorRepository;

    private final ParticipantsEunIndicatorMapper participantsEunIndicatorMapper;

    private final CountriesService countriesService;

    public ParticipantsEunIndicatorServiceImpl(
        ParticipantsEunIndicatorRepository participantsEunIndicatorRepository,
        ParticipantsEunIndicatorMapper participantsEunIndicatorMapper,
        CountriesService countriesService
    ) {
        this.participantsEunIndicatorRepository = participantsEunIndicatorRepository;
        this.participantsEunIndicatorMapper = participantsEunIndicatorMapper;
        this.countriesService = countriesService;
    }

    @Override
    public Indicator<?> getIndicator(Map<String, String> params, Long reportId) {
        Long countryId = params.get("country_id") != null ? Long.parseLong(params.get("country_id")) : null;
        Long startDate = params.get("start_year") != null ? Long.parseLong(params.get("start_year")) : null;
        Long endDate = params.get("end_year") != null ? Long.parseLong(params.get("end_year")) : null;
        String countryCode = this.countriesService.findOne(countryId).map(CountriesDTO::getCountryCode).orElse(null);
        Indicator<ParticipantsEunIndicatorDTO> indicator = new Indicator<>();
        if (countryCode != null) {
            List<ParticipantsEunIndicator> data = this.participantsEunIndicatorRepository.findAllByCountryCodeEquals(countryCode);
            data.removeIf(item -> (startDate != null && item.getPeriod() < startDate) || (endDate != null && item.getPeriod() > endDate));
            long sum = data.stream().mapToLong(ParticipantsEunIndicator::getnCount).sum();
            indicator.setData(participantsEunIndicatorMapper.toDto(data));
            indicator.setValue(String.valueOf(sum));
        }
        indicator.setCode("course_participants_eun");
        indicator.setLabel("Course Participants EUN");
        return indicator;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void fetchDataFromEun() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url + "bycountry/bycourse/participants",
                HttpMethod.GET,
                new HttpEntity<>("{}", headers),
                String.class
            );
            Gson gson = new Gson();
            ApiResponseParticipantDto[] res = gson.fromJson(response.getBody(), ApiResponseParticipantDto[].class);
            for (ApiResponseParticipantDto apiResponseParticipantDto : res) {
                ParticipantsEunIndicator participantsEunIndicator = new ParticipantsEunIndicator();
                participantsEunIndicator.setnCount(apiResponseParticipantDto.getN_count());
                participantsEunIndicator.setPeriod(apiResponseParticipantDto.getPeriod());
                participantsEunIndicator.setCountryCode(apiResponseParticipantDto.getCountry_code());
                participantsEunIndicator.setCourseName(apiResponseParticipantDto.getCourse_name());
                participantsEunIndicator.setCourseId(apiResponseParticipantDto.getCourse_id());

                ParticipantsEunIndicator participantsEunIndicatorRes = participantsEunIndicatorRepository.findByCountryCodeAndCourseId(
                    participantsEunIndicator.getCountryCode(),
                    participantsEunIndicator.getCourseId()
                );

                if (participantsEunIndicatorRes != null) {
                    participantsEunIndicator.setId(participantsEunIndicatorRes.getId());
                    participantsEunIndicator.setLastModifiedBy("system");
                    participantsEunIndicator.setLastModifiedDate(LocalDate.now());
                    participantsEunIndicator.setCreatedBy(participantsEunIndicatorRes.getCreatedBy());
                    participantsEunIndicator.setCreatedDate(participantsEunIndicatorRes.getCreatedDate());
                } else {
                    participantsEunIndicator.setCreatedBy("system");
                    participantsEunIndicator.setCreatedDate(LocalDate.now());
                }
                this.save(participantsEunIndicator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ParticipantsEunIndicatorDTO save(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO) {
        log.debug("Request to save ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);
        ParticipantsEunIndicator participantsEunIndicator = participantsEunIndicatorMapper.toEntity(participantsEunIndicatorDTO);
        participantsEunIndicator = participantsEunIndicatorRepository.save(participantsEunIndicator);
        return participantsEunIndicatorMapper.toDto(participantsEunIndicator);
    }

    @Override
    public ParticipantsEunIndicatorDTO save(ParticipantsEunIndicator participantsEunIndicator) {
        log.debug("Request to save ParticipantsEunIndicator : {}", participantsEunIndicator);
        participantsEunIndicator = participantsEunIndicatorRepository.save(participantsEunIndicator);
        return participantsEunIndicatorMapper.toDto(participantsEunIndicator);
    }

    @Override
    public ParticipantsEunIndicatorDTO update(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO) {
        log.debug("Request to update ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);
        ParticipantsEunIndicator participantsEunIndicator = participantsEunIndicatorMapper.toEntity(participantsEunIndicatorDTO);
        participantsEunIndicator = participantsEunIndicatorRepository.save(participantsEunIndicator);
        return participantsEunIndicatorMapper.toDto(participantsEunIndicator);
    }

    @Override
    public Optional<ParticipantsEunIndicatorDTO> partialUpdate(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO) {
        log.debug("Request to partially update ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);

        return participantsEunIndicatorRepository
            .findById(participantsEunIndicatorDTO.getId())
            .map(existingParticipantsEunIndicator -> {
                participantsEunIndicatorMapper.partialUpdate(existingParticipantsEunIndicator, participantsEunIndicatorDTO);

                return existingParticipantsEunIndicator;
            })
            .map(participantsEunIndicatorRepository::save)
            .map(participantsEunIndicatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantsEunIndicatorDTO> findAll() {
        log.debug("Request to get all ParticipantsEunIndicators");
        return participantsEunIndicatorRepository
            .findAll()
            .stream()
            .map(participantsEunIndicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantsEunIndicatorDTO> findOne(Long id) {
        log.debug("Request to get ParticipantsEunIndicator : {}", id);
        return participantsEunIndicatorRepository.findById(id).map(participantsEunIndicatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParticipantsEunIndicator : {}", id);
        participantsEunIndicatorRepository.deleteById(id);
    }
}
