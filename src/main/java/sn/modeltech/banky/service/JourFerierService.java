package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.JourFerierDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.JourFerier}.
 */
public interface JourFerierService {
    /**
     * Save a jourFerier.
     *
     * @param jourFerierDTO the entity to save.
     * @return the persisted entity.
     */
    JourFerierDTO save(JourFerierDTO jourFerierDTO);

    /**
     * Updates a jourFerier.
     *
     * @param jourFerierDTO the entity to update.
     * @return the persisted entity.
     */
    JourFerierDTO update(JourFerierDTO jourFerierDTO);

    /**
     * Partially updates a jourFerier.
     *
     * @param jourFerierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JourFerierDTO> partialUpdate(JourFerierDTO jourFerierDTO);

    /**
     * Get all the jourFeriers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourFerierDTO> findAll(Pageable pageable);

    /**
     * Get the "id" jourFerier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourFerierDTO> findOne(String id);

    /**
     * Delete the "id" jourFerier.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
