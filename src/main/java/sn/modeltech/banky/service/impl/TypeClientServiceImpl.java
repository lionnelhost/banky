package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.TypeClient;
import sn.modeltech.banky.repository.TypeClientRepository;
import sn.modeltech.banky.service.TypeClientService;
import sn.modeltech.banky.service.dto.TypeClientDTO;
import sn.modeltech.banky.service.mapper.TypeClientMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.TypeClient}.
 */
@Service
@Transactional
public class TypeClientServiceImpl implements TypeClientService {

    private static final Logger LOG = LoggerFactory.getLogger(TypeClientServiceImpl.class);

    private final TypeClientRepository typeClientRepository;

    private final TypeClientMapper typeClientMapper;

    public TypeClientServiceImpl(TypeClientRepository typeClientRepository, TypeClientMapper typeClientMapper) {
        this.typeClientRepository = typeClientRepository;
        this.typeClientMapper = typeClientMapper;
    }

    @Override
    public TypeClientDTO save(TypeClientDTO typeClientDTO) {
        LOG.debug("Request to save TypeClient : {}", typeClientDTO);
        TypeClient typeClient = typeClientMapper.toEntity(typeClientDTO);
        typeClient = typeClientRepository.save(typeClient);
        return typeClientMapper.toDto(typeClient);
    }

    @Override
    public TypeClientDTO update(TypeClientDTO typeClientDTO) {
        LOG.debug("Request to update TypeClient : {}", typeClientDTO);
        TypeClient typeClient = typeClientMapper.toEntity(typeClientDTO);
        typeClient.setIsPersisted();
        typeClient = typeClientRepository.save(typeClient);
        return typeClientMapper.toDto(typeClient);
    }

    @Override
    public Optional<TypeClientDTO> partialUpdate(TypeClientDTO typeClientDTO) {
        LOG.debug("Request to partially update TypeClient : {}", typeClientDTO);

        return typeClientRepository
            .findById(typeClientDTO.getIdTypeClient())
            .map(existingTypeClient -> {
                typeClientMapper.partialUpdate(existingTypeClient, typeClientDTO);

                return existingTypeClient;
            })
            .map(typeClientRepository::save)
            .map(typeClientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeClientDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TypeClients");
        return typeClientRepository.findAll(pageable).map(typeClientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeClientDTO> findOne(String id) {
        LOG.debug("Request to get TypeClient : {}", id);
        return typeClientRepository.findById(id).map(typeClientMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete TypeClient : {}", id);
        typeClientRepository.deleteById(id);
    }
}
