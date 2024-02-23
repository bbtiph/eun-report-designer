package org.eun.back.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.OrganizationEunIndicatorDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.OrganizationEunIndicator}.
 */
public interface OrganizationEunIndicatorService {
    /**
     * Save a organizationEunIndicator.
     *
     * @param organizationEunIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationEunIndicatorDTO save(OrganizationEunIndicatorDTO organizationEunIndicatorDTO);

    OrganizationEunIndicatorDTO save(OrganizationEunIndicator organizationEunIndicator);

    /**
     * Updates a organizationEunIndicator.
     *
     * @param organizationEunIndicatorDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationEunIndicatorDTO update(OrganizationEunIndicatorDTO organizationEunIndicatorDTO);

    /**
     * Partially updates a organizationEunIndicator.
     *
     * @param organizationEunIndicatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationEunIndicatorDTO> partialUpdate(OrganizationEunIndicatorDTO organizationEunIndicatorDTO);

    /**
     * Get all the organizationEunIndicators.
     *
     * @return the list of entities.
     */
    List<OrganizationEunIndicatorDTO> findAll();

    List<OrganizationEunIndicatorDTO> findAllByCountryName(String countryName, Map<String, String> params);

    /**
     * Get the "id" organizationEunIndicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationEunIndicatorDTO> findOne(Long id);

    /**
     * Delete the "id" organizationEunIndicator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Indicator<?> getIndicator(Map<String, String> params, Long reportId);
}
