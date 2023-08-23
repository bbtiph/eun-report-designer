package org.eun.back.service.impl;

import com.google.gson.Gson;
import java.io.IOException;
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
import org.eun.back.repository.OrganizationEunIndicatorRepository;
import org.eun.back.service.OrganizationEunIndicatorService;
import org.eun.back.service.dto.ApiResponseItemDto;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.OrganizationEunIndicatorDTO;
import org.eun.back.service.mapper.OrganizationEunIndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link OrganizationEunIndicator}.
 */
@Service
@Transactional
public class OrganizationEunIndicatorServiceImpl implements OrganizationEunIndicatorService {

    @Value("${eun.dwh.url}")
    private String url;

    private final Logger log = LoggerFactory.getLogger(OrganizationEunIndicatorServiceImpl.class);

    private final OrganizationEunIndicatorRepository organizationEunIndicatorRepository;

    private final OrganizationEunIndicatorMapper organizationEunIndicatorMapper;

    public OrganizationEunIndicatorServiceImpl(
        OrganizationEunIndicatorRepository organizationEunIndicatorRepository,
        OrganizationEunIndicatorMapper organizationEunIndicatorMapper
    ) {
        this.organizationEunIndicatorRepository = organizationEunIndicatorRepository;
        this.organizationEunIndicatorMapper = organizationEunIndicatorMapper;
    }

    @Scheduled(fixedDelay = 60000)
    public void fetchDataFromEun() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url + "bycountry/byproject/organisations",
                HttpMethod.GET,
                new HttpEntity<>("{}", headers),
                String.class
            );
            Gson gson = new Gson();
            ApiResponseItemDto[] res = gson.fromJson(response.getBody(), ApiResponseItemDto[].class);
            for (ApiResponseItemDto apiResponseItemDto : res) {
                System.out.println(apiResponseItemDto);
                OrganizationEunIndicatorDTO organizationEunIndicatorDTO = new OrganizationEunIndicatorDTO();
                organizationEunIndicatorDTO.setnCount(apiResponseItemDto.getN_count());
                organizationEunIndicatorDTO.setCountryId(apiResponseItemDto.getCountry_id());
                organizationEunIndicatorDTO.setProjectId(apiResponseItemDto.getProject_id());
                organizationEunIndicatorDTO.setProjectUrl(apiResponseItemDto.getProject_url());
                organizationEunIndicatorDTO.setCountryName(apiResponseItemDto.getCountry_name());
                organizationEunIndicatorDTO.setProjectName(apiResponseItemDto.getProject_name());
                organizationEunIndicatorDTO.setReportsProjectId(apiResponseItemDto.getReports_project_id());

                OrganizationEunIndicator organizationEunIndicator = organizationEunIndicatorRepository.findByCountryIdAndProjectIdAndReportsProjectId(
                    organizationEunIndicatorDTO.getCountryId(),
                    organizationEunIndicatorDTO.getProjectId(),
                    organizationEunIndicatorDTO.getReportsProjectId()
                );

                if (organizationEunIndicator != null) {
                    organizationEunIndicatorDTO.setId(organizationEunIndicator.getId());
                    organizationEunIndicatorDTO.setLastModifiedBy("system");
                    organizationEunIndicatorDTO.setLastModifiedDate(LocalDate.now());
                } else {
                    organizationEunIndicatorDTO.setCreatedBy("system");
                    organizationEunIndicatorDTO.setCreatedDate(LocalDate.now());
                }
                this.save(organizationEunIndicatorDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Indicator<?> getIndicator(Long countryId, Long reportId) {
        List<OrganizationEunIndicator> data = this.organizationEunIndicatorRepository.findAll();
        long sum = data.stream().mapToLong(OrganizationEunIndicator::getnCount).sum();
        Indicator<OrganizationEunIndicatorDTO> indicator = new Indicator<>();
        indicator.setData(organizationEunIndicatorMapper.toDto(data));
        indicator.setCode("organization_eun");
        indicator.setLabel("Organization EUN");
        indicator.setValue(String.valueOf(sum));
        return indicator;
    }

    @Override
    public OrganizationEunIndicatorDTO save(OrganizationEunIndicatorDTO organizationEunIndicatorDTO) {
        log.debug("Request to save OrganizationEunIndicator : {}", organizationEunIndicatorDTO);
        OrganizationEunIndicator organizationEunIndicator = organizationEunIndicatorMapper.toEntity(organizationEunIndicatorDTO);
        organizationEunIndicator = organizationEunIndicatorRepository.save(organizationEunIndicator);
        return organizationEunIndicatorMapper.toDto(organizationEunIndicator);
    }

    @Override
    public OrganizationEunIndicatorDTO update(OrganizationEunIndicatorDTO organizationEunIndicatorDTO) {
        log.debug("Request to update OrganizationEunIndicator : {}", organizationEunIndicatorDTO);
        OrganizationEunIndicator organizationEunIndicator = organizationEunIndicatorMapper.toEntity(organizationEunIndicatorDTO);
        organizationEunIndicator = organizationEunIndicatorRepository.save(organizationEunIndicator);
        return organizationEunIndicatorMapper.toDto(organizationEunIndicator);
    }

    @Override
    public Optional<OrganizationEunIndicatorDTO> partialUpdate(OrganizationEunIndicatorDTO organizationEunIndicatorDTO) {
        log.debug("Request to partially update OrganizationEunIndicator : {}", organizationEunIndicatorDTO);

        return organizationEunIndicatorRepository
            .findById(organizationEunIndicatorDTO.getId())
            .map(existingOrganizationEunIndicator -> {
                organizationEunIndicatorMapper.partialUpdate(existingOrganizationEunIndicator, organizationEunIndicatorDTO);

                return existingOrganizationEunIndicator;
            })
            .map(organizationEunIndicatorRepository::save)
            .map(organizationEunIndicatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationEunIndicatorDTO> findAll() {
        log.debug("Request to get all OrganizationEunIndicators");
        return organizationEunIndicatorRepository
            .findAll()
            .stream()
            .map(organizationEunIndicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationEunIndicatorDTO> findOne(Long id) {
        log.debug("Request to get OrganizationEunIndicator : {}", id);
        return organizationEunIndicatorRepository.findById(id).map(organizationEunIndicatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationEunIndicator : {}", id);
        organizationEunIndicatorRepository.deleteById(id);
    }

    private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (HttpMethod.GET.equals(httpMethod)) {
                return new HttpEntityEnclosingGetRequestBase(uri);
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
