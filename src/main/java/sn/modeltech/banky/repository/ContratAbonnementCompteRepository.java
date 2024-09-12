package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.ContratAbonnementCompte;

/**
 * Spring Data JPA repository for the ContratAbonnementCompte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratAbonnementCompteRepository extends JpaRepository<ContratAbonnementCompte, Long> {}
