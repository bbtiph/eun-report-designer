package org.eun.back.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.eun.back.domain.JobInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo, Long> {
    List<JobInfo> findAllByStatusProposalAndStartingDateGreaterThanEqualAndEndingDateIsLessThanEqual(
        String statusProposal,
        LocalDate startingDate,
        LocalDate endingDate
    );

    List<JobInfo> findAllByStatusProposal(String statusProposal);

    JobInfo findFirstByExternalId(String externalId);
}
