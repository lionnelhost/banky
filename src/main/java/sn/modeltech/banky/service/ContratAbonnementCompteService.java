package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.ContratAbonnementCompteDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.ContratAbonnementCompte}.
 */
public interface ContratAbonnementCompteService {
    /**
     * Save a contratAbonnementCompte.
     *
     * @param contratAbonnementCompteDTO the entity to save.
     * @return the persisted entity.
     */
    ContratAbonnementCompteDTO save(ContratAbonnementCompteDTO contratAbonnementCompteDTO);

    /**
     * Updates a contratAbonnementCompte.
     *
     * @param contratAbonnementCompteDTO the entity to update.
     * @return the persisted entity.
     */
    ContratAbonnementCompteDTO update(ContratAbonnementCompteDTO contratAbonnementCompteDTO);

    /**
     * Partially updates a contratAbonnementCompte.
     *
     * @param contratAbonnementCompteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContratAbonnementCompteDTO> partialUpdate(ContratAbonnementCompteDTO contratAbonnementCompteDTO);

    /**
     * Get all the contratAbonnementComptes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContratAbonnementCompteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" contratAbonnementCompte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContratAbonnementCompteDTO> findOne(Long id);

    /**
     * Delete the "id" contratAbonnementCompte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
