package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.DispositifSignatureDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.DispositifSignature}.
 */
public interface DispositifSignatureService {
    /**
     * Save a dispositifSignature.
     *
     * @param dispositifSignatureDTO the entity to save.
     * @return the persisted entity.
     */
    DispositifSignatureDTO save(DispositifSignatureDTO dispositifSignatureDTO);

    /**
     * Updates a dispositifSignature.
     *
     * @param dispositifSignatureDTO the entity to update.
     * @return the persisted entity.
     */
    DispositifSignatureDTO update(DispositifSignatureDTO dispositifSignatureDTO);

    /**
     * Partially updates a dispositifSignature.
     *
     * @param dispositifSignatureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispositifSignatureDTO> partialUpdate(DispositifSignatureDTO dispositifSignatureDTO);

    /**
     * Get all the dispositifSignatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispositifSignatureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dispositifSignature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispositifSignatureDTO> findOne(String id);

    /**
     * Delete the "id" dispositifSignature.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
