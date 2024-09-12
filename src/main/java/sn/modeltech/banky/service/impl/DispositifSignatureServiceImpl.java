package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.DispositifSignature;
import sn.modeltech.banky.repository.DispositifSignatureRepository;
import sn.modeltech.banky.service.DispositifSignatureService;
import sn.modeltech.banky.service.dto.DispositifSignatureDTO;
import sn.modeltech.banky.service.mapper.DispositifSignatureMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.DispositifSignature}.
 */
@Service
@Transactional
public class DispositifSignatureServiceImpl implements DispositifSignatureService {

    private static final Logger LOG = LoggerFactory.getLogger(DispositifSignatureServiceImpl.class);

    private final DispositifSignatureRepository dispositifSignatureRepository;

    private final DispositifSignatureMapper dispositifSignatureMapper;

    public DispositifSignatureServiceImpl(
        DispositifSignatureRepository dispositifSignatureRepository,
        DispositifSignatureMapper dispositifSignatureMapper
    ) {
        this.dispositifSignatureRepository = dispositifSignatureRepository;
        this.dispositifSignatureMapper = dispositifSignatureMapper;
    }

    @Override
    public DispositifSignatureDTO save(DispositifSignatureDTO dispositifSignatureDTO) {
        LOG.debug("Request to save DispositifSignature : {}", dispositifSignatureDTO);
        DispositifSignature dispositifSignature = dispositifSignatureMapper.toEntity(dispositifSignatureDTO);
        dispositifSignature = dispositifSignatureRepository.save(dispositifSignature);
        return dispositifSignatureMapper.toDto(dispositifSignature);
    }

    @Override
    public DispositifSignatureDTO update(DispositifSignatureDTO dispositifSignatureDTO) {
        LOG.debug("Request to update DispositifSignature : {}", dispositifSignatureDTO);
        DispositifSignature dispositifSignature = dispositifSignatureMapper.toEntity(dispositifSignatureDTO);
        dispositifSignature.setIsPersisted();
        dispositifSignature = dispositifSignatureRepository.save(dispositifSignature);
        return dispositifSignatureMapper.toDto(dispositifSignature);
    }

    @Override
    public Optional<DispositifSignatureDTO> partialUpdate(DispositifSignatureDTO dispositifSignatureDTO) {
        LOG.debug("Request to partially update DispositifSignature : {}", dispositifSignatureDTO);

        return dispositifSignatureRepository
            .findById(dispositifSignatureDTO.getIdDispositif())
            .map(existingDispositifSignature -> {
                dispositifSignatureMapper.partialUpdate(existingDispositifSignature, dispositifSignatureDTO);

                return existingDispositifSignature;
            })
            .map(dispositifSignatureRepository::save)
            .map(dispositifSignatureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispositifSignatureDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DispositifSignatures");
        return dispositifSignatureRepository.findAll(pageable).map(dispositifSignatureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispositifSignatureDTO> findOne(String id) {
        LOG.debug("Request to get DispositifSignature : {}", id);
        return dispositifSignatureRepository.findById(id).map(dispositifSignatureMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete DispositifSignature : {}", id);
        dispositifSignatureRepository.deleteById(id);
    }
}
