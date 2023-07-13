package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.CountriesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.Countries}.
 */
public interface CountriesService {
    /**
     * Save a countries.
     *
     * @param countriesDTO the entity to save.
     * @return the persisted entity.
     */
    CountriesDTO save(CountriesDTO countriesDTO);

    /**
     * Updates a countries.
     *
     * @param countriesDTO the entity to update.
     * @return the persisted entity.
     */
    CountriesDTO update(CountriesDTO countriesDTO);

    /**
     * Partially updates a countries.
     *
     * @param countriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CountriesDTO> partialUpdate(CountriesDTO countriesDTO);

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CountriesDTO> findAll(Pageable pageable);

    List<CountriesDTO> findAll(Long reportId);

    /**
     * Get the "id" countries.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountriesDTO> findOne(Long id);

    /**
     * Delete the "id" countries.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
