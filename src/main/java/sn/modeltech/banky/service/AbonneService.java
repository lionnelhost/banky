package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.AbonneDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.Abonne}.
 */
public interface AbonneService {
    /**
     * Save a abonne.
     *
     * @param abonneDTO the entity to save.
     * @return the persisted entity.
     */
    AbonneDTO save(AbonneDTO abonneDTO);

    /**
     * Updates a abonne.
     *
     * @param abonneDTO the entity to update.
     * @return the persisted entity.
     */
    AbonneDTO update(AbonneDTO abonneDTO);

    /**
     * Partially updates a abonne.
     *
     * @param abonneDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AbonneDTO> partialUpdate(AbonneDTO abonneDTO);

    /**
     * Get all the abonnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AbonneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" abonne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AbonneDTO> findOne(String id);

    /**
     * Delete the "id" abonne.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
