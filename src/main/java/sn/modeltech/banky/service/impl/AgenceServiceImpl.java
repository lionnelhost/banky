package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.Agence;
import sn.modeltech.banky.repository.AgenceRepository;
import sn.modeltech.banky.service.AgenceService;
import sn.modeltech.banky.service.dto.AgenceDTO;
import sn.modeltech.banky.service.mapper.AgenceMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.Agence}.
 */
@Service
@Transactional
public class AgenceServiceImpl implements AgenceService {

    private static final Logger LOG = LoggerFactory.getLogger(AgenceServiceImpl.class);

    private final AgenceRepository agenceRepository;

    private final AgenceMapper agenceMapper;

    public AgenceServiceImpl(AgenceRepository agenceRepository, AgenceMapper agenceMapper) {
        this.agenceRepository = agenceRepository;
        this.agenceMapper = agenceMapper;
    }

    @Override
    public AgenceDTO save(AgenceDTO agenceDTO) {
        LOG.debug("Request to save Agence : {}", agenceDTO);
        Agence agence = agenceMapper.toEntity(agenceDTO);
        agence = agenceRepository.save(agence);
        return agenceMapper.toDto(agence);
    }

    @Override
    public AgenceDTO update(AgenceDTO agenceDTO) {
        LOG.debug("Request to update Agence : {}", agenceDTO);
        Agence agence = agenceMapper.toEntity(agenceDTO);
        agence.setIsPersisted();
        agence = agenceRepository.save(agence);
        return agenceMapper.toDto(agence);
    }

    @Override
    public Optional<AgenceDTO> partialUpdate(AgenceDTO agenceDTO) {
        LOG.debug("Request to partially update Agence : {}", agenceDTO);

        return agenceRepository
            .findById(agenceDTO.getIdAgence())
            .map(existingAgence -> {
                agenceMapper.partialUpdate(existingAgence, agenceDTO);

                return existingAgence;
            })
            .map(agenceRepository::save)
            .map(agenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Agences");
        return agenceRepository.findAll(pageable).map(agenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgenceDTO> findOne(String id) {
        LOG.debug("Request to get Agence : {}", id);
        return agenceRepository.findById(id).map(agenceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Agence : {}", id);
        agenceRepository.deleteById(id);
    }
}
