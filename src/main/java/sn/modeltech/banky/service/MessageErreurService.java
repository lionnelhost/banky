package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.MessageErreurDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.MessageErreur}.
 */
public interface MessageErreurService {
    /**
     * Save a messageErreur.
     *
     * @param messageErreurDTO the entity to save.
     * @return the persisted entity.
     */
    MessageErreurDTO save(MessageErreurDTO messageErreurDTO);

    /**
     * Updates a messageErreur.
     *
     * @param messageErreurDTO the entity to update.
     * @return the persisted entity.
     */
    MessageErreurDTO update(MessageErreurDTO messageErreurDTO);

    /**
     * Partially updates a messageErreur.
     *
     * @param messageErreurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MessageErreurDTO> partialUpdate(MessageErreurDTO messageErreurDTO);

    /**
     * Get all the messageErreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageErreurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" messageErreur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MessageErreurDTO> findOne(String id);

    /**
     * Delete the "id" messageErreur.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
