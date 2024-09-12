package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.AgenceDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.Agence}.
 */
public interface AgenceService {
    /**
     * Save a agence.
     *
     * @param agenceDTO the entity to save.
     * @return the persisted entity.
     */
    AgenceDTO save(AgenceDTO agenceDTO);

    /**
     * Updates a agence.
     *
     * @param agenceDTO the entity to update.
     * @return the persisted entity.
     */
    AgenceDTO update(AgenceDTO agenceDTO);

    /**
     * Partially updates a agence.
     *
     * @param agenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgenceDTO> partialUpdate(AgenceDTO agenceDTO);

    /**
     * Get all the agences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgenceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" agence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgenceDTO> findOne(String id);

    /**
     * Delete the "id" agence.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
