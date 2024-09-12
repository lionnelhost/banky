package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.TypeClientDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.TypeClient}.
 */
public interface TypeClientService {
    /**
     * Save a typeClient.
     *
     * @param typeClientDTO the entity to save.
     * @return the persisted entity.
     */
    TypeClientDTO save(TypeClientDTO typeClientDTO);

    /**
     * Updates a typeClient.
     *
     * @param typeClientDTO the entity to update.
     * @return the persisted entity.
     */
    TypeClientDTO update(TypeClientDTO typeClientDTO);

    /**
     * Partially updates a typeClient.
     *
     * @param typeClientDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeClientDTO> partialUpdate(TypeClientDTO typeClientDTO);

    /**
     * Get all the typeClients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeClientDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeClientDTO> findOne(String id);

    /**
     * Delete the "id" typeClient.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
