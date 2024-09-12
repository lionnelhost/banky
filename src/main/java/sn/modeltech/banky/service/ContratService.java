package sn.modeltech.banky.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.ContratDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.Contrat}.
 */
public interface ContratService {
    /**
     * Save a contrat.
     *
     * @param contratDTO the entity to save.
     * @return the persisted entity.
     */
    ContratDTO save(ContratDTO contratDTO);

    /**
     * Updates a contrat.
     *
     * @param contratDTO the entity to update.
     * @return the persisted entity.
     */
    ContratDTO update(ContratDTO contratDTO);

    /**
     * Partially updates a contrat.
     *
     * @param contratDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContratDTO> partialUpdate(ContratDTO contratDTO);

    /**
     * Get all the contrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContratDTO> findAll(Pageable pageable);

    /**
     * Get all the ContratDTO where Client is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ContratDTO> findAllWhereClientIsNull();

    /**
     * Get the "id" contrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContratDTO> findOne(String id);

    /**
     * Delete the "id" contrat.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
