package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.Funding;
import org.eun.back.repository.FundingRepository;
import org.eun.back.service.FundingService;
import org.eun.back.service.dto.FundingDTO;
import org.eun.back.service.mapper.FundingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Funding}.
 */
@Service
@Transactional
public class FundingServiceImpl implements FundingService {

    private final Logger log = LoggerFactory.getLogger(FundingServiceImpl.class);

    private final FundingRepository fundingRepository;

    private final FundingMapper fundingMapper;

    public FundingServiceImpl(FundingRepository fundingRepository, FundingMapper fundingMapper) {
        this.fundingRepository = fundingRepository;
        this.fundingMapper = fundingMapper;
    }

    @Override
    public FundingDTO save(FundingDTO fundingDTO) {
        log.debug("Request to save Funding : {}", fundingDTO);
        Funding funding = fundingMapper.toEntity(fundingDTO);
        funding = fundingRepository.save(funding);
        return fundingMapper.toDto(funding);
    }

    @Override
    public FundingDTO update(FundingDTO fundingDTO) {
        log.debug("Request to update Funding : {}", fundingDTO);
        Funding funding = fundingMapper.toEntity(fundingDTO);
        funding = fundingRepository.save(funding);
        return fundingMapper.toDto(funding);
    }

    @Override
    public Optional<FundingDTO> partialUpdate(FundingDTO fundingDTO) {
        log.debug("Request to partially update Funding : {}", fundingDTO);

        return fundingRepository
            .findById(fundingDTO.getId())
            .map(existingFunding -> {
                fundingMapper.partialUpdate(existingFunding, fundingDTO);

                return existingFunding;
            })
            .map(fundingRepository::save)
            .map(fundingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FundingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fundings");
        return fundingRepository.findAll(pageable).map(fundingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FundingDTO> findOne(Long id) {
        log.debug("Request to get Funding : {}", id);
        return fundingRepository.findById(id).map(fundingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Funding : {}", id);
        fundingRepository.deleteById(id);
    }
}
