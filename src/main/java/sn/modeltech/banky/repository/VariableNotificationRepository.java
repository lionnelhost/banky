package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.VariableNotification;

/**
 * Spring Data JPA repository for the VariableNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariableNotificationRepository extends JpaRepository<VariableNotification, String> {}
