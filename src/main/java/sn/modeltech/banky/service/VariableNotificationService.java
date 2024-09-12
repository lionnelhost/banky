package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.VariableNotificationDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.VariableNotification}.
 */
public interface VariableNotificationService {
    /**
     * Save a variableNotification.
     *
     * @param variableNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    VariableNotificationDTO save(VariableNotificationDTO variableNotificationDTO);

    /**
     * Updates a variableNotification.
     *
     * @param variableNotificationDTO the entity to update.
     * @return the persisted entity.
     */
    VariableNotificationDTO update(VariableNotificationDTO variableNotificationDTO);

    /**
     * Partially updates a variableNotification.
     *
     * @param variableNotificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VariableNotificationDTO> partialUpdate(VariableNotificationDTO variableNotificationDTO);

    /**
     * Get all the variableNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VariableNotificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" variableNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VariableNotificationDTO> findOne(String id);

    /**
     * Delete the "id" variableNotification.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
