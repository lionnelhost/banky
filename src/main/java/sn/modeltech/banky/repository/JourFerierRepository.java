package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.JourFerier;

/**
 * Spring Data JPA repository for the JourFerier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourFerierRepository extends JpaRepository<JourFerier, String> {}
