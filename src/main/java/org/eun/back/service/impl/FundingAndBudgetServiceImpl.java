package org.eun.back.service.impl;

import java.util.List;
import org.eun.back.domain.FundingAndBudget;
import org.eun.back.domain.JobInfo;
import org.eun.back.repository.FundingAndBudgetRepository;
import org.eun.back.service.FundingAndBudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobInfo}.
 */
@Service
@Transactional
public class FundingAndBudgetServiceImpl implements FundingAndBudgetService {

    private final Logger log = LoggerFactory.getLogger(FundingAndBudgetServiceImpl.class);
    private final FundingAndBudgetRepository fundingAndBudgetRepository;

    public FundingAndBudgetServiceImpl(FundingAndBudgetRepository fundingAndBudgetRepository) {
        this.fundingAndBudgetRepository = fundingAndBudgetRepository;
    }

    @Override
    public List<FundingAndBudget> findAll() {
        return fundingAndBudgetRepository.findAll();
    }
}
