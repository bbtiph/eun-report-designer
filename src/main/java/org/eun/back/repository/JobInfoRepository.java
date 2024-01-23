package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.JobInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo, Long> {
    List<JobInfo> findAllByStatusProposal(String statusProposal);
}
