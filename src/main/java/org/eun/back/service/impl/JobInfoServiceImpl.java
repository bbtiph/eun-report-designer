package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.JobInfo;
import org.eun.back.repository.JobInfoRepository;
import org.eun.back.service.JobInfoService;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.service.mapper.JobInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobInfo}.
 */
@Service
@Transactional
public class JobInfoServiceImpl implements JobInfoService {

    private final Logger log = LoggerFactory.getLogger(JobInfoServiceImpl.class);

    private final JobInfoRepository jobInfoRepository;

    private final JobInfoMapper jobInfoMapper;

    public JobInfoServiceImpl(JobInfoRepository jobInfoRepository, JobInfoMapper jobInfoMapper) {
        this.jobInfoRepository = jobInfoRepository;
        this.jobInfoMapper = jobInfoMapper;
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
}
