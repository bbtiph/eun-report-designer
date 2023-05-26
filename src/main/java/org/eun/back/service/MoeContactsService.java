package org.eun.back.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.MoeContactsDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link org.eun.back.domain.MoeContacts}.
 */
public interface MoeContactsService {
    /**
     * Save a moeContacts.
     *
     * @param moeContactsDTO the entity to save.
     * @return the persisted entity.
     */
    MoeContactsDTO save(MoeContactsDTO moeContactsDTO);

    /**
     * Updates a moeContacts.
     *
     * @param moeContactsDTO the entity to update.
     * @return the persisted entity.
     */
    MoeContactsDTO update(MoeContactsDTO moeContactsDTO);

    /**
     * Partially updates a moeContacts.
     *
     * @param moeContactsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MoeContactsDTO> partialUpdate(MoeContactsDTO moeContactsDTO);

    /**
     * Get all the moeContacts.
     *
     * @return the list of entities.
     */
    List<MoeContactsDTO> findAll();

    /**
     * Get the "id" moeContacts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MoeContactsDTO> findOne(Long id);

    /**
     * Delete the "id" moeContacts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void upload(MultipartFile file) throws IOException;
}
