package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.BanqueDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.Banque}.
 */
public interface BanqueService {
    /**
     * Save a banque.
     *
     * @param banqueDTO the entity to save.
     * @return the persisted entity.
     */
    BanqueDTO save(BanqueDTO banqueDTO);

    /**
     * Updates a banque.
     *
     * @param banqueDTO the entity to update.
     * @return the persisted entity.
     */
    BanqueDTO update(BanqueDTO banqueDTO);

    /**
     * Partially updates a banque.
     *
     * @param banqueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BanqueDTO> partialUpdate(BanqueDTO banqueDTO);

    /**
     * Get all the banques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BanqueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" banque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BanqueDTO> findOne(String id);

    /**
     * Delete the "id" banque.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
