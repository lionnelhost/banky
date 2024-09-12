package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.MessageErreur;

/**
 * Spring Data JPA repository for the MessageErreur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageErreurRepository extends JpaRepository<MessageErreur, String> {}
