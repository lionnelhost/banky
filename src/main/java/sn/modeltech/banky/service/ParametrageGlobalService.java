package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.ParametrageGlobalDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.ParametrageGlobal}.
 */
public interface ParametrageGlobalService {
    /**
     * Save a parametrageGlobal.
     *
     * @param parametrageGlobalDTO the entity to save.
     * @return the persisted entity.
     */
    ParametrageGlobalDTO save(ParametrageGlobalDTO parametrageGlobalDTO);

    /**
     * Updates a parametrageGlobal.
     *
     * @param parametrageGlobalDTO the entity to update.
     * @return the persisted entity.
     */
    ParametrageGlobalDTO update(ParametrageGlobalDTO parametrageGlobalDTO);

    /**
     * Partially updates a parametrageGlobal.
     *
     * @param parametrageGlobalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParametrageGlobalDTO> partialUpdate(ParametrageGlobalDTO parametrageGlobalDTO);

    /**
     * Get all the parametrageGlobals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParametrageGlobalDTO> findAll(Pageable pageable);

    /**
     * Get the "id" parametrageGlobal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParametrageGlobalDTO> findOne(String id);

    /**
     * Delete the "id" parametrageGlobal.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
