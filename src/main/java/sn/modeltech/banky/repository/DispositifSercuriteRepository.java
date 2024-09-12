package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.DispositifSercurite;

/**
 * Spring Data JPA repository for the DispositifSercurite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositifSercuriteRepository extends JpaRepository<DispositifSercurite, Long> {}
