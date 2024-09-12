package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.ParametrageGlobal;

/**
 * Spring Data JPA repository for the ParametrageGlobal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametrageGlobalRepository extends JpaRepository<ParametrageGlobal, String> {}
