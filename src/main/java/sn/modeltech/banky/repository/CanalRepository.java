package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.Canal;

/**
 * Spring Data JPA repository for the Canal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CanalRepository extends JpaRepository<Canal, String> {}
