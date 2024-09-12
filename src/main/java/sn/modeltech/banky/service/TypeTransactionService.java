package sn.modeltech.banky.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.modeltech.banky.service.dto.TypeTransactionDTO;

/**
 * Service Interface for managing {@link sn.modeltech.banky.domain.TypeTransaction}.
 */
public interface TypeTransactionService {
    /**
     * Save a typeTransaction.
     *
     * @param typeTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    TypeTransactionDTO save(TypeTransactionDTO typeTransactionDTO);

    /**
     * Updates a typeTransaction.
     *
     * @param typeTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    TypeTransactionDTO update(TypeTransactionDTO typeTransactionDTO);

    /**
     * Partially updates a typeTransaction.
     *
     * @param typeTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeTransactionDTO> partialUpdate(TypeTransactionDTO typeTransactionDTO);

    /**
     * Get all the typeTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeTransactionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeTransactionDTO> findOne(String id);

    /**
     * Delete the "id" typeTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
