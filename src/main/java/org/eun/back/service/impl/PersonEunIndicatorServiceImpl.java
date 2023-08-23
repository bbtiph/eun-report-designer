package org.eun.back.service.impl;

import com.google.gson.Gson;
import java.net.URI;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.domain.PersonEunIndicator;
import org.eun.back.domain.Project;
import org.eun.back.repository.PersonEunIndicatorRepository;
import org.eun.back.service.PersonEunIndicatorService;
import org.eun.back.service.dto.*;
import org.eun.back.service.mapper.PersonEunIndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link PersonEunIndicator}.
 */
@Service
@Transactional
public class PersonEunIndicatorServiceImpl implements PersonEunIndicatorService {

    @Value("${eun.dwh.url}")
    private String url;

    private final Logger log = LoggerFactory.getLogger(PersonEunIndicatorServiceImpl.class);

    private final PersonEunIndicatorRepository personEunIndicatorRepository;

    private final PersonEunIndicatorMapper personEunIndicatorMapper;

    public PersonEunIndicatorServiceImpl(
        PersonEunIndicatorRepository personEunIndicatorRepository,
        PersonEunIndicatorMapper personEunIndicatorMapper
    ) {
        this.personEunIndicatorRepository = personEunIndicatorRepository;
        this.personEunIndicatorMapper = personEunIndicatorMapper;
    }

    @Scheduled(fixedDelay = 60000)
    public void fetchDataFromEun() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate(new PersonEunIndicatorServiceImpl.CustomHttpComponentsClientHttpRequestFactory());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url + "bycountry/byproject/persons",
                HttpMethod.GET,
                new HttpEntity<>("{}", headers),
                String.class
            );

            Gson gson = new Gson();
            ApiResponseItemDto[] res = gson.fromJson(response.getBody(), ApiResponseItemDto[].class);
            for (ApiResponseItemDto apiResponseItemDto : res) {
                System.out.println(apiResponseItemDto);
                PersonEunIndicatorDTO personEunIndicatorDTO = new PersonEunIndicatorDTO();
                personEunIndicatorDTO.setnCount(apiResponseItemDto.getN_count());
                personEunIndicatorDTO.setCountryId(apiResponseItemDto.getCountry_id());
                personEunIndicatorDTO.setProjectId(apiResponseItemDto.getProject_id());
                personEunIndicatorDTO.setProjectUrl(apiResponseItemDto.getProject_url());
                personEunIndicatorDTO.setCountryName(apiResponseItemDto.getCountry_name());
                personEunIndicatorDTO.setProjectName(apiResponseItemDto.getProject_name());
                personEunIndicatorDTO.setReportsProjectId(apiResponseItemDto.getReports_project_id());

                PersonEunIndicator personEunIndicator = personEunIndicatorRepository.findByCountryIdAndProjectIdAndReportsProjectId(
                    personEunIndicatorDTO.getCountryId(),
                    personEunIndicatorDTO.getProjectId(),
                    personEunIndicatorDTO.getReportsProjectId()
                );

                if (personEunIndicator != null) {
                    personEunIndicatorDTO.setId(personEunIndicator.getId());
                    personEunIndicatorDTO.setLastModifiedBy("system");
                    personEunIndicatorDTO.setLastModifiedDate(LocalDate.now());
                } else {
                    personEunIndicatorDTO.setCreatedBy("system");
                    personEunIndicatorDTO.setCreatedDate(LocalDate.now());
                }
                this.save(personEunIndicatorDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Indicator<?> getIndicator(Long countryId, Long reportId) {
        List<PersonEunIndicator> data = this.personEunIndicatorRepository.findAll();
        Indicator<PersonEunIndicatorDTO> indicator = new Indicator<>();
        long sum = data.stream().mapToLong(PersonEunIndicator::getnCount).sum();
        indicator.setData(personEunIndicatorMapper.toDto(data));
        indicator.setCode("person_eun");
        indicator.setLabel("Persons EUN");
        indicator.setValue(String.valueOf(sum));
        return indicator;
    }

    @Override
    public PersonEunIndicatorDTO save(PersonEunIndicatorDTO personEunIndicatorDTO) {
        log.debug("Request to save PersonEunIndicator : {}", personEunIndicatorDTO);
        PersonEunIndicator personEunIndicator = personEunIndicatorMapper.toEntity(personEunIndicatorDTO);
        personEunIndicator = personEunIndicatorRepository.save(personEunIndicator);
        return personEunIndicatorMapper.toDto(personEunIndicator);
    }

    @Override
    public PersonEunIndicatorDTO update(PersonEunIndicatorDTO personEunIndicatorDTO) {
        log.debug("Request to update PersonEunIndicator : {}", personEunIndicatorDTO);
        PersonEunIndicator personEunIndicator = personEunIndicatorMapper.toEntity(personEunIndicatorDTO);
        personEunIndicator = personEunIndicatorRepository.save(personEunIndicator);
        return personEunIndicatorMapper.toDto(personEunIndicator);
    }

    @Override
    public Optional<PersonEunIndicatorDTO> partialUpdate(PersonEunIndicatorDTO personEunIndicatorDTO) {
        log.debug("Request to partially update PersonEunIndicator : {}", personEunIndicatorDTO);

        return personEunIndicatorRepository
            .findById(personEunIndicatorDTO.getId())
            .map(existingPersonEunIndicator -> {
                personEunIndicatorMapper.partialUpdate(existingPersonEunIndicator, personEunIndicatorDTO);

                return existingPersonEunIndicator;
            })
            .map(personEunIndicatorRepository::save)
            .map(personEunIndicatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonEunIndicatorDTO> findAll() {
        log.debug("Request to get all PersonEunIndicators");
        return personEunIndicatorRepository
            .findAll()
            .stream()
            .map(personEunIndicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEunIndicatorDTO> findOne(Long id) {
        log.debug("Request to get PersonEunIndicator : {}", id);
        return personEunIndicatorRepository.findById(id).map(personEunIndicatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonEunIndicator : {}", id);
        personEunIndicatorRepository.deleteById(id);
    }

    private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (HttpMethod.GET.equals(httpMethod)) {
                return new PersonEunIndicatorServiceImpl.HttpEntityEnclosingGetRequestBase(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {

        public HttpEntityEnclosingGetRequestBase(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }
}
