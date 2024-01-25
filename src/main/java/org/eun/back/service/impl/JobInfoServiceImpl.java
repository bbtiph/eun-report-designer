package org.eun.back.service.impl;

import com.google.gson.Gson;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.eun.back.domain.JobInfo;
import org.eun.back.repository.JobInfoRepository;
import org.eun.back.service.JobInfoService;
import org.eun.back.service.dto.JobInfoContentFromERPDTO;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.service.dto.JobInfoFromERPDTO;
import org.eun.back.service.mapper.JobInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link JobInfo}.
 */
@Service
@Transactional
public class JobInfoServiceImpl implements JobInfoService {

    @Value("${eun.erp.url}")
    private String url;

    @Value("${eun.erp.login.url}")
    private String loginUrl;

    @Value("${eun.erp.login.grant-type}")
    private String grantType;

    @Value("${eun.erp.login.scope}")
    private String scope;

    @Value("${eun.erp.login.client-id}")
    private String clientId;

    @Value("${eun.erp.login.client-secret}")
    private String clientSecret;

    private final Logger log = LoggerFactory.getLogger(JobInfoServiceImpl.class);

    private final JobInfoRepository jobInfoRepository;

    private final JobInfoMapper jobInfoMapper;

    public JobInfoServiceImpl(JobInfoRepository jobInfoRepository, JobInfoMapper jobInfoMapper) {
        this.jobInfoRepository = jobInfoRepository;
        this.jobInfoMapper = jobInfoMapper;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void fetchDataFromERP() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken());
        RestTemplate restTemplate = new RestTemplate(new JobInfoServiceImpl.CustomHttpComponentsClientHttpRequestFactory());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url + "/jobInfo",
                HttpMethod.GET,
                new HttpEntity<>("{}", headers),
                String.class
            );

            Gson gson = new Gson();
            JobInfoFromERPDTO res = gson.fromJson(response.getBody(), JobInfoFromERPDTO.class);
            for (JobInfoContentFromERPDTO jobInfoDTO : res.getValue()) {
                JobInfo jobInfo = new JobInfo();
                jobInfo.setExternalId(jobInfoDTO.getId());
                jobInfo.setOdataEtag(jobInfo.getOdataEtag());
                jobInfo.setJobNumber(jobInfoDTO.getJobNumber());
                jobInfo.setDescription(jobInfoDTO.getDescription());
                jobInfo.setDescription2(jobInfoDTO.getDescription2());
                jobInfo.setBillToCustomerNo(jobInfoDTO.getBillToCustomerNo());
                jobInfo.setBillToName(jobInfoDTO.getBillToName());
                jobInfo.setBillToCountryRegionCode(jobInfoDTO.getBillToCountryRegionCode());
                jobInfo.setSellToContact(jobInfoDTO.getSellToContact());
                jobInfo.setYourReference(jobInfoDTO.getYourReference());
                jobInfo.setContractNo(jobInfoDTO.getContractNo());
                jobInfo.setStatusProposal(jobInfoDTO.getStatusProposal());
                jobInfo.setStartingDate(LocalDate.parse(jobInfoDTO.getStartingDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                jobInfo.setEndingDate(LocalDate.parse(jobInfoDTO.getEndingDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                jobInfo.setDurationInMonths(jobInfoDTO.getDurationInMonths());
                jobInfo.setProjectManager(jobInfoDTO.getProjectManager());
                jobInfo.setProjectManagerMail(jobInfoDTO.getProjectManagerMail());
                jobInfo.setEunRole(jobInfoDTO.getEunRole());
                jobInfo.setVisaNumber(jobInfoDTO.getVisaNumber());
                jobInfo.setJobType(jobInfoDTO.getJobType());
                jobInfo.setJobTypeText(jobInfoDTO.getJobTypeText());
                jobInfo.setJobProgram(jobInfoDTO.getJobProgram());
                jobInfo.setProgramManager(jobInfoDTO.getProgramManager());
                jobInfo.setBudgetEUN(jobInfoDTO.getBudgetEUN());
                jobInfo.setFundingEUN(jobInfoDTO.getFundingEUN());
                jobInfo.setFundingRate(jobInfoDTO.getFundingRate());
                jobInfo.setBudgetConsortium(jobInfoDTO.getBudgetConsortium());
                jobInfo.setFundingConsortium(jobInfoDTO.getFundingConsortium());
                jobInfo.setOverheadPerc(jobInfoDTO.getOverheadPerc().intValue());

                JobInfo jobInfoRes = jobInfoRepository.findFirstByExternalId(jobInfo.getExternalId());
                if (jobInfoRes != null) {
                    jobInfo.setId(jobInfoRes.getId());
                }
                jobInfoRepository.save(jobInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getToken() throws JSONException {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("scope", scope);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(loginUrl, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            JSONObject json = new JSONObject(responseBody);
            return json.getString("access_token");
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public JobInfoDTO save(JobInfoDTO jobInfoDTO) {
        log.debug("Request to save JobInfo : {}", jobInfoDTO);
        JobInfo jobInfo = jobInfoMapper.toEntity(jobInfoDTO);
        jobInfo = jobInfoRepository.save(jobInfo);
        return jobInfoMapper.toDto(jobInfo);
    }

    @Override
    public JobInfoDTO update(JobInfoDTO jobInfoDTO) {
        log.debug("Request to update JobInfo : {}", jobInfoDTO);
        JobInfo jobInfo = jobInfoMapper.toEntity(jobInfoDTO);
        jobInfo = jobInfoRepository.save(jobInfo);
        return jobInfoMapper.toDto(jobInfo);
    }

    @Override
    public Optional<JobInfoDTO> partialUpdate(JobInfoDTO jobInfoDTO) {
        log.debug("Request to partially update JobInfo : {}", jobInfoDTO);

        return jobInfoRepository
            .findById(jobInfoDTO.getId())
            .map(existingJobInfo -> {
                jobInfoMapper.partialUpdate(existingJobInfo, jobInfoDTO);

                return existingJobInfo;
            })
            .map(jobInfoRepository::save)
            .map(jobInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobInfoDTO> findAll() {
        log.debug("Request to get all JobInfos");
        return jobInfoRepository.findAll().stream().map(jobInfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobInfoDTO> findAllByStatusProposal(String statusProposal, Map<String, String> params) {
        log.debug("Request to get all JobInfos");
        if (params != null && params.containsKey("fromDate") && params.containsKey("toDate")) {
            LocalDate startDate = LocalDate.parse(params.get("fromDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(params.get("toDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return jobInfoRepository
                .findAllByStatusProposalAndStartingDateGreaterThanEqualAndEndingDateIsLessThanEqual(statusProposal, startDate, endDate)
                .stream()
                .map(jobInfoMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        return jobInfoRepository
            .findAllByStatusProposal(statusProposal)
            .stream()
            .map(jobInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobInfoDTO> findOne(Long id) {
        log.debug("Request to get JobInfo : {}", id);
        return jobInfoRepository.findById(id).map(jobInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobInfo : {}", id);
        jobInfoRepository.deleteById(id);
    }

    private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (HttpMethod.GET.equals(httpMethod)) {
                return new JobInfoServiceImpl.HttpEntityEnclosingGetRequestBase(uri);
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
