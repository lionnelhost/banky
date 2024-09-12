package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.CompteBancaireDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.CompteBancaire}.
 */
public interface CompteBancaireService {
    /**
     * Save a compteBancaire.
     *
     * @param compteBancaireDTO the entity to save.
     * @return the persisted entity.
     */
    CompteBancaireDTO save(CompteBancaireDTO compteBancaireDTO);

    /**
     * Updates a compteBancaire.
     *
     * @param compteBancaireDTO the entity to update.
     * @return the persisted entity.
     */
    CompteBancaireDTO update(CompteBancaireDTO compteBancaireDTO);

    /**
     * Partially updates a compteBancaire.
     *
     * @param compteBancaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompteBancaireDTO> partialUpdate(CompteBancaireDTO compteBancaireDTO);

    /**
     * Get all the compteBancaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompteBancaireDTO> findAll(Pageable pageable);

    /**
     * Get the "id" compteBancaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompteBancaireDTO> findOne(String id);

    /**
     * Delete the "id" compteBancaire.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
