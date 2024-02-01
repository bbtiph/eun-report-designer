package org.eun.back.repository;

import org.eun.back.domain.FundingAndBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface FundingAndBudgetRepository extends JpaRepository<FundingAndBudget, Long> {}
