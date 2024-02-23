package org.eun.back.service.impl;

import com.google.gson.Gson;
import java.net.URI;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.repository.OrganizationEunIndicatorRepository;
import org.eun.back.service.CountriesService;
import org.eun.back.service.OrganizationEunIndicatorService;
import org.eun.back.service.dto.*;
import org.eun.back.service.mapper.OrganizationEunIndicatorMapper;
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

    private final CountriesService countriesService;

    public OrganizationEunIndicatorServiceImpl(
        OrganizationEunIndicatorRepository organizationEunIndicatorRepository,
        OrganizationEunIndicatorMapper organizationEunIndicatorMapper,
        CountriesService countriesService
    ) {
        this.organizationEunIndicatorRepository = organizationEunIndicatorRepository;
        this.organizationEunIndicatorMapper = organizationEunIndicatorMapper;
        this.countriesService = countriesService;
    }

    @Scheduled(cron = "0 0 * * * *")
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
            ApiResponseOrganizationAndPersonDto[] res = gson.fromJson(response.getBody(), ApiResponseOrganizationAndPersonDto[].class);
            for (ApiResponseOrganizationAndPersonDto apiResponseOrganizationAndPersonDto : res) {
                OrganizationEunIndicator organizationEunIndicator = new OrganizationEunIndicator();
                organizationEunIndicator.setnCount(apiResponseOrganizationAndPersonDto.getN_count());
                organizationEunIndicator.setPeriod(apiResponseOrganizationAndPersonDto.getPeriod());
                organizationEunIndicator.setCountryId(apiResponseOrganizationAndPersonDto.getCountry_id());
                organizationEunIndicator.setProjectId(apiResponseOrganizationAndPersonDto.getProject_id());
                organizationEunIndicator.setProjectUrl(apiResponseOrganizationAndPersonDto.getProject_url());
                organizationEunIndicator.setCountryName(apiResponseOrganizationAndPersonDto.getCountry_name());
                organizationEunIndicator.setProjectName(apiResponseOrganizationAndPersonDto.getProject_name());
                organizationEunIndicator.setReportsProjectId(apiResponseOrganizationAndPersonDto.getReports_project_id());

                OrganizationEunIndicator organizationEunIndicatorRes = organizationEunIndicatorRepository.findByCountryIdAndProjectIdAndReportsProjectId(
                    organizationEunIndicator.getCountryId(),
                    organizationEunIndicator.getProjectId(),
                    organizationEunIndicator.getReportsProjectId()
                );

                if (organizationEunIndicatorRes != null) {
                    organizationEunIndicator.setId(organizationEunIndicatorRes.getId());
                    organizationEunIndicator.setLastModifiedBy("system");
                    organizationEunIndicator.setLastModifiedDate(LocalDate.now());
                    organizationEunIndicator.setCreatedBy(organizationEunIndicatorRes.getCreatedBy());
                    organizationEunIndicator.setCreatedDate(organizationEunIndicatorRes.getCreatedDate());
                } else {
                    organizationEunIndicator.setCreatedBy("system");
                    organizationEunIndicator.setCreatedDate(LocalDate.now());
                }
                this.save(organizationEunIndicator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Indicator<?> getIndicator(Map<String, String> params, Long reportId) {
        Long countryId = params.get("country_id") != null ? Long.parseLong(params.get("country_id")) : null;
        Long startDate = params.get("start_year") != null ? Long.parseLong(params.get("start_year")) : null;
        Long endDate = params.get("end_year") != null ? Long.parseLong(params.get("end_year")) : null;
        String countryName = this.countriesService.findOne(countryId).map(CountriesDTO::getCountryName).orElse(null);
        Indicator<OrganizationEunIndicatorDTO> indicator = new Indicator<>();
        if (countryName != null) {
            List<OrganizationEunIndicator> data = this.organizationEunIndicatorRepository.findAllByCountryNameEquals(countryName);
            data.removeIf(item -> (startDate != null && item.getPeriod() < startDate) || (endDate != null && item.getPeriod() > endDate));
            long sum = data.stream().mapToLong(OrganizationEunIndicator::getnCount).sum();
            indicator.setData(organizationEunIndicatorMapper.toDto(data));
            indicator.setValue(String.valueOf(sum));
        }
        indicator.setCode("organization_eun");
        indicator.setLabel("Organization EUN");
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
    public OrganizationEunIndicatorDTO save(OrganizationEunIndicator organizationEunIndicator) {
        log.debug("Request to save OrganizationEunIndicator : {}", organizationEunIndicator);
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
    public List<OrganizationEunIndicatorDTO> findAllByCountryName(String countryName, Map<String, String> params) {
        log.debug("Request to get all OrganizationEunIndicators");
        String beginDateParam = params.get("fromDate");
        String endDateParam = params.get("toDate");

        Long startYear = parseYear(beginDateParam);
        Long endYear = parseYear(endDateParam);

        List<OrganizationEunIndicator> indicators;
        if (startYear != null && endYear != null) {
            indicators =
                organizationEunIndicatorRepository.findAllByCountryNameEqualsAndPeriodBetweenOrderByPeriod(countryName, startYear, endYear);
        } else {
            indicators = organizationEunIndicatorRepository.findAllByCountryNameEqualsOrderByPeriod(countryName);
        }

        return indicators.stream().map(organizationEunIndicatorMapper::toDto).collect(Collectors.toList());
    }

    private Long parseYear(String date) {
        if (date != null && !date.isEmpty()) {
            try {
                return Long.parseLong(date.split("-")[0]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }
        return null;
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
