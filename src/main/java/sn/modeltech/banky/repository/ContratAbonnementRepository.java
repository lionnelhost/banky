package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.ContratAbonnement;

/**
 * Spring Data JPA repository for the ContratAbonnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratAbonnementRepository extends JpaRepository<ContratAbonnement, Long> {}
