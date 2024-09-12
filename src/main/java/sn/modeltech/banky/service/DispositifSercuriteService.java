package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.DispositifSercuriteDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.DispositifSercurite}.
 */
public interface DispositifSercuriteService {
    /**
     * Save a dispositifSercurite.
     *
     * @param dispositifSercuriteDTO the entity to save.
     * @return the persisted entity.
     */
    DispositifSercuriteDTO save(DispositifSercuriteDTO dispositifSercuriteDTO);

    /**
     * Updates a dispositifSercurite.
     *
     * @param dispositifSercuriteDTO the entity to update.
     * @return the persisted entity.
     */
    DispositifSercuriteDTO update(DispositifSercuriteDTO dispositifSercuriteDTO);

    /**
     * Partially updates a dispositifSercurite.
     *
     * @param dispositifSercuriteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispositifSercuriteDTO> partialUpdate(DispositifSercuriteDTO dispositifSercuriteDTO);

    /**
     * Get all the dispositifSercurites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispositifSercuriteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dispositifSercurite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispositifSercuriteDTO> findOne(Long id);

    /**
     * Delete the "id" dispositifSercurite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
