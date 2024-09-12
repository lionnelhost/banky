package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.CanalDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.Canal}.
 */
public interface CanalService {
    /**
     * Save a canal.
     *
     * @param canalDTO the entity to save.
     * @return the persisted entity.
     */
    CanalDTO save(CanalDTO canalDTO);

    /**
     * Updates a canal.
     *
     * @param canalDTO the entity to update.
     * @return the persisted entity.
     */
    CanalDTO update(CanalDTO canalDTO);

    /**
     * Partially updates a canal.
     *
     * @param canalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CanalDTO> partialUpdate(CanalDTO canalDTO);

    /**
     * Get all the canals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CanalDTO> findAll(Pageable pageable);

    /**
     * Get the "id" canal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CanalDTO> findOne(String id);

    /**
     * Delete the "id" canal.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
