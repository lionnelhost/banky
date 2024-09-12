package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.TypeTransaction;

/**
 * Spring Data JPA repository for the TypeTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTransactionRepository extends JpaRepository<TypeTransaction, String> {}
