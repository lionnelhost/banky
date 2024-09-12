package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.Canal;
import sn.modeltech.banky.repository.CanalRepository;
import sn.modeltech.banky.service.CanalService;
import sn.modeltech.banky.service.dto.CanalDTO;
import sn.modeltech.banky.service.mapper.CanalMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.Canal}.
 */
@Service
@Transactional
public class CanalServiceImpl implements CanalService {

    private static final Logger LOG = LoggerFactory.getLogger(CanalServiceImpl.class);

    private final CanalRepository canalRepository;

    private final CanalMapper canalMapper;

    public CanalServiceImpl(CanalRepository canalRepository, CanalMapper canalMapper) {
        this.canalRepository = canalRepository;
        this.canalMapper = canalMapper;
    }

    @Override
    public CanalDTO save(CanalDTO canalDTO) {
        LOG.debug("Request to save Canal : {}", canalDTO);
        Canal canal = canalMapper.toEntity(canalDTO);
        canal = canalRepository.save(canal);
        return canalMapper.toDto(canal);
    }

    @Override
    public CanalDTO update(CanalDTO canalDTO) {
        LOG.debug("Request to update Canal : {}", canalDTO);
        Canal canal = canalMapper.toEntity(canalDTO);
        canal.setIsPersisted();
        canal = canalRepository.save(canal);
        return canalMapper.toDto(canal);
    }

    @Override
    public Optional<CanalDTO> partialUpdate(CanalDTO canalDTO) {
        LOG.debug("Request to partially update Canal : {}", canalDTO);

        return canalRepository
            .findById(canalDTO.getIdCanal())
            .map(existingCanal -> {
                canalMapper.partialUpdate(existingCanal, canalDTO);

                return existingCanal;
            })
            .map(canalRepository::save)
            .map(canalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CanalDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Canals");
        return canalRepository.findAll(pageable).map(canalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CanalDTO> findOne(String id) {
        LOG.debug("Request to get Canal : {}", id);
        return canalRepository.findById(id).map(canalMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Canal : {}", id);
        canalRepository.deleteById(id);
    }
}
