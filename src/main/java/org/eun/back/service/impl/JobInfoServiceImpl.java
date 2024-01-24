package org.eun.back.service.impl;

import com.google.gson.Gson;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link JobInfo}.
 */
@Service
@Transactional
public class JobInfoServiceImpl implements JobInfoService {

    @Value("${eun.erp.url}")
    private String url;

    private final Logger log = LoggerFactory.getLogger(JobInfoServiceImpl.class);

    private final JobInfoRepository jobInfoRepository;

    private final JobInfoMapper jobInfoMapper;

    public JobInfoServiceImpl(JobInfoRepository jobInfoRepository, JobInfoMapper jobInfoMapper) {
        this.jobInfoRepository = jobInfoRepository;
        this.jobInfoMapper = jobInfoMapper;
    }

    @Scheduled(cron = "0/30 * * * * *")
    //    @Scheduled(cron = "0 0 10 * * *")
    public void fetchDataFromERP() {
        HttpHeaders headers = new HttpHeaders();
        //      TODO: Implement logic auth with Scapta;
        String authToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSIsImtpZCI6IjVCM25SeHRRN2ppOGVORGMzRnkwNUtmOTdaRSJ9.eyJhdWQiOiJodHRwczovL2FwaS5idXNpbmVzc2NlbnRyYWwuZHluYW1pY3MuY29tIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvZTIxZDE4ZjEtMjExMi00ZWNmLWE2N2MtZDIwYWVkYmQxOGIzLyIsImlhdCI6MTcwNjAxNjY4MywibmJmIjoxNzA2MDE2NjgzLCJleHAiOjE3MDYwMjA1ODMsImFpbyI6IkUyVmdZR0NhOGJQbHdyZFZmTGZuTjBwOTcrbG1Bd0E9IiwiYXBwaWQiOiJkM2ZmZWRkYy0wYzU0LTQ0NGQtOTA1NS0xMzMwNmM0ZTAxMmIiLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9lMjFkMThmMS0yMTEyLTRlY2YtYTY3Yy1kMjBhZWRiZDE4YjMvIiwiaWR0eXAiOiJhcHAiLCJvaWQiOiJlMWUxY2Q1Yi03NjRkLTRlNDUtOTU1YS01NTQ2MTI4Y2U3YWMiLCJyaCI6IjAuQVFVQThSZ2Q0aEloejA2bWZOSUs3YjBZc3ozdmJabHNzMU5CaGdlbV9Ud0J1SjhiQVFBLiIsInJvbGVzIjpbImFwcF9hY2Nlc3MiLCJBUEkuUmVhZFdyaXRlLkFsbCJdLCJzdWIiOiJlMWUxY2Q1Yi03NjRkLTRlNDUtOTU1YS01NTQ2MTI4Y2U3YWMiLCJ0aWQiOiJlMjFkMThmMS0yMTEyLTRlY2YtYTY3Yy1kMjBhZWRiZDE4YjMiLCJ1dGkiOiI5c1NDR08xY1VrQ19na2F5Rmp5T0FBIiwidmVyIjoiMS4wIn0.QcW4GXjla0QbXw_GPeLI0EzpjZJK3m-Y7uPsUSFKG9pGk4RHM4sY-LZnEPpJMzJhzalClg23gJA4y_0bPwzaOqZMHr6aZRmVdiJxUxdG_hqd90axoktjosy7zfOD4UACsuNJN-Eqg-pDecjZTa0Nga0jHGYXS_3VMnt8c4UBpF7eITyhzLMF3yMKZOPypbx-heJTX8XLQiFH31-7GrTWPmYcGbHaHU6QlvFu7STVQ_s2sZrPxpD08I6r0MxaoiX5C6LNCud4q_mcGNPYe6bxDvdJhBTe-2PTbMm5vGout40Sk2WoITR6gQ83vFA38Cyn3iTBeWRapyDcR6rRlaJwsA";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authToken);
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
    public List<JobInfoDTO> findAllByStatusProposal(String statusProposal) {
        log.debug("Request to get all JobInfos");
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
