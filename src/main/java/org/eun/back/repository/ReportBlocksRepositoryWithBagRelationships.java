package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.ReportBlocks;
import org.springframework.data.domain.Page;

public interface ReportBlocksRepositoryWithBagRelationships {
    Optional<ReportBlocks> fetchBagRelationships(Optional<ReportBlocks> reportBlocks);

    List<ReportBlocks> fetchBagRelationships(List<ReportBlocks> reportBlocks);

    Page<ReportBlocks> fetchBagRelationships(Page<ReportBlocks> reportBlocks);
}
