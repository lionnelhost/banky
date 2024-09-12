package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.TypeContrat;
import sn.modeltech.banky.repository.TypeContratRepository;
import sn.modeltech.banky.service.TypeContratService;
import sn.modeltech.banky.service.dto.TypeContratDTO;
import sn.modeltech.banky.service.mapper.TypeContratMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.TypeContrat}.
 */
@Service
@Transactional
public class TypeContratServiceImpl implements TypeContratService {

    private static final Logger LOG = LoggerFactory.getLogger(TypeContratServiceImpl.class);

    private final TypeContratRepository typeContratRepository;

    private final TypeContratMapper typeContratMapper;

    public TypeContratServiceImpl(TypeContratRepository typeContratRepository, TypeContratMapper typeContratMapper) {
        this.typeContratRepository = typeContratRepository;
        this.typeContratMapper = typeContratMapper;
    }

    @Override
    public TypeContratDTO save(TypeContratDTO typeContratDTO) {
        LOG.debug("Request to save TypeContrat : {}", typeContratDTO);
        TypeContrat typeContrat = typeContratMapper.toEntity(typeContratDTO);
        typeContrat = typeContratRepository.save(typeContrat);
        return typeContratMapper.toDto(typeContrat);
    }

    @Override
    public TypeContratDTO update(TypeContratDTO typeContratDTO) {
        LOG.debug("Request to update TypeContrat : {}", typeContratDTO);
        TypeContrat typeContrat = typeContratMapper.toEntity(typeContratDTO);
        typeContrat.setIsPersisted();
        typeContrat = typeContratRepository.save(typeContrat);
        return typeContratMapper.toDto(typeContrat);
    }

    @Override
    public Optional<TypeContratDTO> partialUpdate(TypeContratDTO typeContratDTO) {
        LOG.debug("Request to partially update TypeContrat : {}", typeContratDTO);

        return typeContratRepository
            .findById(typeContratDTO.getIdTypeContrat())
            .map(existingTypeContrat -> {
                typeContratMapper.partialUpdate(existingTypeContrat, typeContratDTO);

                return existingTypeContrat;
            })
            .map(typeContratRepository::save)
            .map(typeContratMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeContratDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TypeContrats");
        return typeContratRepository.findAll(pageable).map(typeContratMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeContratDTO> findOne(String id) {
        LOG.debug("Request to get TypeContrat : {}", id);
        return typeContratRepository.findById(id).map(typeContratMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete TypeContrat : {}", id);
        typeContratRepository.deleteById(id);
    }
}
