package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.Countries;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Countries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long> {
    @Query(
        "SELECT DISTINCT c FROM Countries c " +
        "JOIN c.reportBlockIds rb " +
        "JOIN rb.reportIds r " +
        "WHERE :reportId = r.id " +
        "ORDER BY c.countryName ASC"
    )
    List<Countries> findCountriesByReportId(@Param("reportId") Long reportId);

    @Query("SELECT DISTINCT c FROM Countries c ORDER BY c.countryName ASC")
    List<Countries> findAll();

    Countries findFirstByCountryNameIgnoreCase(String countryName);
}
