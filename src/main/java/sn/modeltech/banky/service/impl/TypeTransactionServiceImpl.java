package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.TypeTransaction;
import sn.modeltech.banky.repository.TypeTransactionRepository;
import sn.modeltech.banky.service.TypeTransactionService;
import sn.modeltech.banky.service.dto.TypeTransactionDTO;
import sn.modeltech.banky.service.mapper.TypeTransactionMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.TypeTransaction}.
 */
@Service
@Transactional
public class TypeTransactionServiceImpl implements TypeTransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TypeTransactionServiceImpl.class);

    private final TypeTransactionRepository typeTransactionRepository;

    private final TypeTransactionMapper typeTransactionMapper;

    public TypeTransactionServiceImpl(TypeTransactionRepository typeTransactionRepository, TypeTransactionMapper typeTransactionMapper) {
        this.typeTransactionRepository = typeTransactionRepository;
        this.typeTransactionMapper = typeTransactionMapper;
    }

    @Override
    public TypeTransactionDTO save(TypeTransactionDTO typeTransactionDTO) {
        LOG.debug("Request to save TypeTransaction : {}", typeTransactionDTO);
        TypeTransaction typeTransaction = typeTransactionMapper.toEntity(typeTransactionDTO);
        typeTransaction = typeTransactionRepository.save(typeTransaction);
        return typeTransactionMapper.toDto(typeTransaction);
    }

    @Override
    public TypeTransactionDTO update(TypeTransactionDTO typeTransactionDTO) {
        LOG.debug("Request to update TypeTransaction : {}", typeTransactionDTO);
        TypeTransaction typeTransaction = typeTransactionMapper.toEntity(typeTransactionDTO);
        typeTransaction.setIsPersisted();
        typeTransaction = typeTransactionRepository.save(typeTransaction);
        return typeTransactionMapper.toDto(typeTransaction);
    }

    @Override
    public Optional<TypeTransactionDTO> partialUpdate(TypeTransactionDTO typeTransactionDTO) {
        LOG.debug("Request to partially update TypeTransaction : {}", typeTransactionDTO);

        return typeTransactionRepository
            .findById(typeTransactionDTO.getIdTypeTransaction())
            .map(existingTypeTransaction -> {
                typeTransactionMapper.partialUpdate(existingTypeTransaction, typeTransactionDTO);

                return existingTypeTransaction;
            })
            .map(typeTransactionRepository::save)
            .map(typeTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeTransactionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TypeTransactions");
        return typeTransactionRepository.findAll(pageable).map(typeTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeTransactionDTO> findOne(String id) {
        LOG.debug("Request to get TypeTransaction : {}", id);
        return typeTransactionRepository.findById(id).map(typeTransactionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete TypeTransaction : {}", id);
        typeTransactionRepository.deleteById(id);
    }
}
