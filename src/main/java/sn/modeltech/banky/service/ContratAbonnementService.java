package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.ContratAbonnementDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.ContratAbonnement}.
 */
public interface ContratAbonnementService {
    /**
     * Save a contratAbonnement.
     *
     * @param contratAbonnementDTO the entity to save.
     * @return the persisted entity.
     */
    ContratAbonnementDTO save(ContratAbonnementDTO contratAbonnementDTO);

    /**
     * Updates a contratAbonnement.
     *
     * @param contratAbonnementDTO the entity to update.
     * @return the persisted entity.
     */
    ContratAbonnementDTO update(ContratAbonnementDTO contratAbonnementDTO);

    /**
     * Partially updates a contratAbonnement.
     *
     * @param contratAbonnementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContratAbonnementDTO> partialUpdate(ContratAbonnementDTO contratAbonnementDTO);

    /**
     * Get all the contratAbonnements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContratAbonnementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" contratAbonnement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContratAbonnementDTO> findOne(Long id);

    /**
     * Delete the "id" contratAbonnement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
