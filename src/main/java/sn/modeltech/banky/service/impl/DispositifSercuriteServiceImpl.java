package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.DispositifSercurite;
import sn.modeltech.banky.repository.DispositifSercuriteRepository;
import sn.modeltech.banky.service.DispositifSercuriteService;
import sn.modeltech.banky.service.dto.DispositifSercuriteDTO;
import sn.modeltech.banky.service.mapper.DispositifSercuriteMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.DispositifSercurite}.
 */
@Service
@Transactional
public class DispositifSercuriteServiceImpl implements DispositifSercuriteService {

    private static final Logger LOG = LoggerFactory.getLogger(DispositifSercuriteServiceImpl.class);

    private final DispositifSercuriteRepository dispositifSercuriteRepository;

    private final DispositifSercuriteMapper dispositifSercuriteMapper;

    public DispositifSercuriteServiceImpl(
        DispositifSercuriteRepository dispositifSercuriteRepository,
        DispositifSercuriteMapper dispositifSercuriteMapper
    ) {
        this.dispositifSercuriteRepository = dispositifSercuriteRepository;
        this.dispositifSercuriteMapper = dispositifSercuriteMapper;
    }

    @Override
    public DispositifSercuriteDTO save(DispositifSercuriteDTO dispositifSercuriteDTO) {
        LOG.debug("Request to save DispositifSercurite : {}", dispositifSercuriteDTO);
        DispositifSercurite dispositifSercurite = dispositifSercuriteMapper.toEntity(dispositifSercuriteDTO);
        dispositifSercurite = dispositifSercuriteRepository.save(dispositifSercurite);
        return dispositifSercuriteMapper.toDto(dispositifSercurite);
    }

    @Override
    public DispositifSercuriteDTO update(DispositifSercuriteDTO dispositifSercuriteDTO) {
        LOG.debug("Request to update DispositifSercurite : {}", dispositifSercuriteDTO);
        DispositifSercurite dispositifSercurite = dispositifSercuriteMapper.toEntity(dispositifSercuriteDTO);
        dispositifSercurite = dispositifSercuriteRepository.save(dispositifSercurite);
        return dispositifSercuriteMapper.toDto(dispositifSercurite);
    }

    @Override
    public Optional<DispositifSercuriteDTO> partialUpdate(DispositifSercuriteDTO dispositifSercuriteDTO) {
        LOG.debug("Request to partially update DispositifSercurite : {}", dispositifSercuriteDTO);

        return dispositifSercuriteRepository
            .findById(dispositifSercuriteDTO.getId())
            .map(existingDispositifSercurite -> {
                dispositifSercuriteMapper.partialUpdate(existingDispositifSercurite, dispositifSercuriteDTO);

                return existingDispositifSercurite;
            })
            .map(dispositifSercuriteRepository::save)
            .map(dispositifSercuriteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispositifSercuriteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DispositifSercurites");
        return dispositifSercuriteRepository.findAll(pageable).map(dispositifSercuriteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispositifSercuriteDTO> findOne(Long id) {
        LOG.debug("Request to get DispositifSercurite : {}", id);
        return dispositifSercuriteRepository.findById(id).map(dispositifSercuriteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DispositifSercurite : {}", id);
        dispositifSercuriteRepository.deleteById(id);
    }
}
