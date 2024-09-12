package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.ParametrageNotificationDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.ParametrageNotification}.
 */
public interface ParametrageNotificationService {
    /**
     * Save a parametrageNotification.
     *
     * @param parametrageNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    ParametrageNotificationDTO save(ParametrageNotificationDTO parametrageNotificationDTO);

    /**
     * Updates a parametrageNotification.
     *
     * @param parametrageNotificationDTO the entity to update.
     * @return the persisted entity.
     */
    ParametrageNotificationDTO update(ParametrageNotificationDTO parametrageNotificationDTO);

    /**
     * Partially updates a parametrageNotification.
     *
     * @param parametrageNotificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParametrageNotificationDTO> partialUpdate(ParametrageNotificationDTO parametrageNotificationDTO);

    /**
     * Get all the parametrageNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParametrageNotificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" parametrageNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParametrageNotificationDTO> findOne(String id);

    /**
     * Delete the "id" parametrageNotification.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
