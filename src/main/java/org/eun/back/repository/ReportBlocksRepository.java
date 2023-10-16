package org.eun.back.repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.domain.ReportBlocksContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportBlocks entity.
 *
 * When extending this class, extend ReportBlocksRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ReportBlocksRepository extends ReportBlocksRepositoryWithBagRelationships, JpaRepository<ReportBlocks, Long> {
    default Optional<ReportBlocks> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    @Query(
        "SELECT DISTINCT rb FROM ReportBlocks rb LEFT JOIN FETCH rb.reportBlocksContents rbc " +
        "LEFT JOIN FETCH rbc.reportBlocksContentData rbcd " +
        "LEFT JOIN FETCH rb.reportIds r " +
        "WHERE  :reportId = r.id AND (rbcd.country.id IS NULL OR rbcd.country.id = :countryId) " +
        "AND (:countryId MEMBER OF rb.countryIds)"
    )
    List<ReportBlocks> findReportBlocksByCountryAndReport(@Param("countryId") Long countryId, @Param("reportId") Long reportId);

    @Query(
        "SELECT DISTINCT rb FROM ReportBlocks rb LEFT JOIN FETCH rb.reportBlocksContents rbc " +
        "LEFT JOIN FETCH rbc.reportBlocksContentData rbcd " +
        "LEFT JOIN FETCH rb.reportIds r " +
        "WHERE :reportId = r.id AND (rbcd.country.id IS NULL OR rbcd.country.id = :countryId) " +
        "AND (:countryId MEMBER OF rb.countryIds) AND rb.name like 'Participation in%'"
    )
    List<ReportBlocks> findParticipationInXReportBlocksByCountryAndReport(
        @Param("countryId") Long countryId,
        @Param("reportId") Long reportId
    );

    //    @Query("SELECT MAX(rb.priorityNumber) FROM ReportBlocks rb WHERE rb.reportIds.id = :reportId")
    //    Long findMaxPriorityNumberByReportId(@Param("reportId") Long reportId);

    default List<ReportBlocks> findReportBlocksByCountryWithEmptyContent(Long countryId, Long reportId) {
        List<ReportBlocks> reportBlocksList = findReportBlocksByCountryAndReport(countryId, reportId);

        for (ReportBlocks reportBlocks : reportBlocksList) {
            if (reportBlocks.getReportBlocksContents() == null || reportBlocks.getReportBlocksContents().isEmpty()) {
                ReportBlocksContent emptyContent = new ReportBlocksContent();
                emptyContent.setReportBlocks(reportBlocks);
                reportBlocks.setReportBlocksContents(new HashSet<>(Collections.singletonList(emptyContent)));
            }
        }

        return reportBlocksList;
    }

    //    List<ReportBlocks> findAllByReportIdAndCountryIds_IdAndReportBlocksContents_ReportBlocksContentData_Country_Id(
    //        Long reportId,
    //        Long reportBlockCountryId,
    //        Long reportBlockContentDataCountryId
    //    );

    //    List<ReportBlocks> findAllByReportId(Long reportId);

    //    default List<ReportBlocks> findAllWithEagerRelationships() {
    //        return this.fetchBagRelationships(this.findAll());
    //    }

    default List<ReportBlocks> findAllWithEagerRelationshipsByReport(Long reportId, Long countryId) {
        return this.fetchBagRelationships(this.findReportBlocksByCountryWithEmptyContent(countryId, reportId));
    }

    default Page<ReportBlocks> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
