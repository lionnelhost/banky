package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.TypeContratDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.TypeContrat}.
 */
public interface TypeContratService {
    /**
     * Save a typeContrat.
     *
     * @param typeContratDTO the entity to save.
     * @return the persisted entity.
     */
    TypeContratDTO save(TypeContratDTO typeContratDTO);

    /**
     * Updates a typeContrat.
     *
     * @param typeContratDTO the entity to update.
     * @return the persisted entity.
     */
    TypeContratDTO update(TypeContratDTO typeContratDTO);

    /**
     * Partially updates a typeContrat.
     *
     * @param typeContratDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeContratDTO> partialUpdate(TypeContratDTO typeContratDTO);

    /**
     * Get all the typeContrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeContratDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeContrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeContratDTO> findOne(String id);

    /**
     * Delete the "id" typeContrat.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
