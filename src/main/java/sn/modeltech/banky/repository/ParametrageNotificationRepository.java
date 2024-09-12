package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.ParametrageNotification;

/**
 * Spring Data JPA repository for the ParametrageNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametrageNotificationRepository extends JpaRepository<ParametrageNotification, String> {}
