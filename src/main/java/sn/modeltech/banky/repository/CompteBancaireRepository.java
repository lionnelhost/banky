package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.CompteBancaire;

/**
 * Spring Data JPA repository for the CompteBancaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, String> {}
