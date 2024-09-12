package sn.modeltech.banky.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.modeltech.banky.domain.DispositifSignature;

/**
 * Spring Data JPA repository for the DispositifSignature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositifSignatureRepository extends JpaRepository<DispositifSignature, String> {}
