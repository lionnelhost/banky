package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.TypeClient;

/**
 * Spring Data JPA repository for the TypeClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeClientRepository extends JpaRepository<TypeClient, String> {}
