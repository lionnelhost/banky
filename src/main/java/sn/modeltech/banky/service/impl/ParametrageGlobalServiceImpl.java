package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.ParametrageGlobal;
import sn.modeltech.banky.repository.ParametrageGlobalRepository;
import sn.modeltech.banky.service.ParametrageGlobalService;
import sn.modeltech.banky.service.dto.ParametrageGlobalDTO;
import sn.modeltech.banky.service.mapper.ParametrageGlobalMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.ParametrageGlobal}.
 */
@Service
@Transactional
public class ParametrageGlobalServiceImpl implements ParametrageGlobalService {

    private static final Logger LOG = LoggerFactory.getLogger(ParametrageGlobalServiceImpl.class);

    private final ParametrageGlobalRepository parametrageGlobalRepository;

    private final ParametrageGlobalMapper parametrageGlobalMapper;

    public ParametrageGlobalServiceImpl(
        ParametrageGlobalRepository parametrageGlobalRepository,
        ParametrageGlobalMapper parametrageGlobalMapper
    ) {
        this.parametrageGlobalRepository = parametrageGlobalRepository;
        this.parametrageGlobalMapper = parametrageGlobalMapper;
    }

    @Override
    public ParametrageGlobalDTO save(ParametrageGlobalDTO parametrageGlobalDTO) {
        LOG.debug("Request to save ParametrageGlobal : {}", parametrageGlobalDTO);
        ParametrageGlobal parametrageGlobal = parametrageGlobalMapper.toEntity(parametrageGlobalDTO);
        parametrageGlobal = parametrageGlobalRepository.save(parametrageGlobal);
        return parametrageGlobalMapper.toDto(parametrageGlobal);
    }

    @Override
    public ParametrageGlobalDTO update(ParametrageGlobalDTO parametrageGlobalDTO) {
        LOG.debug("Request to update ParametrageGlobal : {}", parametrageGlobalDTO);
        ParametrageGlobal parametrageGlobal = parametrageGlobalMapper.toEntity(parametrageGlobalDTO);
        parametrageGlobal.setIsPersisted();
        parametrageGlobal = parametrageGlobalRepository.save(parametrageGlobal);
        return parametrageGlobalMapper.toDto(parametrageGlobal);
    }

    @Override
    public Optional<ParametrageGlobalDTO> partialUpdate(ParametrageGlobalDTO parametrageGlobalDTO) {
        LOG.debug("Request to partially update ParametrageGlobal : {}", parametrageGlobalDTO);

        return parametrageGlobalRepository
            .findById(parametrageGlobalDTO.getIdParamGlobal())
            .map(existingParametrageGlobal -> {
                parametrageGlobalMapper.partialUpdate(existingParametrageGlobal, parametrageGlobalDTO);

                return existingParametrageGlobal;
            })
            .map(parametrageGlobalRepository::save)
            .map(parametrageGlobalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParametrageGlobalDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ParametrageGlobals");
        return parametrageGlobalRepository.findAll(pageable).map(parametrageGlobalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParametrageGlobalDTO> findOne(String id) {
        LOG.debug("Request to get ParametrageGlobal : {}", id);
        return parametrageGlobalRepository.findById(id).map(parametrageGlobalMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete ParametrageGlobal : {}", id);
        parametrageGlobalRepository.deleteById(id);
    }
}
