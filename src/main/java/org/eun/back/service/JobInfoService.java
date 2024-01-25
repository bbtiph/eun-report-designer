package org.eun.back.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eun.back.service.dto.JobInfoDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.JobInfo}.
 */
public interface JobInfoService {
    /**
     * Save a jobInfo.
     *
     * @param jobInfoDTO the entity to save.
     * @return the persisted entity.
     */
    JobInfoDTO save(JobInfoDTO jobInfoDTO);

    /**
     * Updates a jobInfo.
     *
     * @param jobInfoDTO the entity to update.
     * @return the persisted entity.
     */
    JobInfoDTO update(JobInfoDTO jobInfoDTO);

    /**
     * Partially updates a jobInfo.
     *
     * @param jobInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobInfoDTO> partialUpdate(JobInfoDTO jobInfoDTO);

    /**
     * Get all the jobInfos.
     *
     * @return the list of entities.
     */
    List<JobInfoDTO> findAll();

    List<JobInfoDTO> findAllByStatusProposal(String statusProposal, Map<String, String> params);

    /**
     * Get the "id" jobInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobInfoDTO> findOne(Long id);

    /**
     * Delete the "id" jobInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
