package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.JourFerier;
import sn.modeltech.banky.repository.JourFerierRepository;
import sn.modeltech.banky.service.JourFerierService;
import sn.modeltech.banky.service.dto.JourFerierDTO;
import sn.modeltech.banky.service.mapper.JourFerierMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.JourFerier}.
 */
@Service
@Transactional
public class JourFerierServiceImpl implements JourFerierService {

    private static final Logger LOG = LoggerFactory.getLogger(JourFerierServiceImpl.class);

    private final JourFerierRepository jourFerierRepository;

    private final JourFerierMapper jourFerierMapper;

    public JourFerierServiceImpl(JourFerierRepository jourFerierRepository, JourFerierMapper jourFerierMapper) {
        this.jourFerierRepository = jourFerierRepository;
        this.jourFerierMapper = jourFerierMapper;
    }

    @Override
    public JourFerierDTO save(JourFerierDTO jourFerierDTO) {
        LOG.debug("Request to save JourFerier : {}", jourFerierDTO);
        JourFerier jourFerier = jourFerierMapper.toEntity(jourFerierDTO);
        jourFerier = jourFerierRepository.save(jourFerier);
        return jourFerierMapper.toDto(jourFerier);
    }

    @Override
    public JourFerierDTO update(JourFerierDTO jourFerierDTO) {
        LOG.debug("Request to update JourFerier : {}", jourFerierDTO);
        JourFerier jourFerier = jourFerierMapper.toEntity(jourFerierDTO);
        jourFerier.setIsPersisted();
        jourFerier = jourFerierRepository.save(jourFerier);
        return jourFerierMapper.toDto(jourFerier);
    }

    @Override
    public Optional<JourFerierDTO> partialUpdate(JourFerierDTO jourFerierDTO) {
        LOG.debug("Request to partially update JourFerier : {}", jourFerierDTO);

        return jourFerierRepository
            .findById(jourFerierDTO.getIdJourFerie())
            .map(existingJourFerier -> {
                jourFerierMapper.partialUpdate(existingJourFerier, jourFerierDTO);

                return existingJourFerier;
            })
            .map(jourFerierRepository::save)
            .map(jourFerierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JourFerierDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all JourFeriers");
        return jourFerierRepository.findAll(pageable).map(jourFerierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JourFerierDTO> findOne(String id) {
        LOG.debug("Request to get JourFerier : {}", id);
        return jourFerierRepository.findById(id).map(jourFerierMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete JourFerier : {}", id);
        jourFerierRepository.deleteById(id);
    }
}
