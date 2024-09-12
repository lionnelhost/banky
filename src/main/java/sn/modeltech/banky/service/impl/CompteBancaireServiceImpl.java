package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.CompteBancaire;
import sn.modeltech.banky.repository.CompteBancaireRepository;
import sn.modeltech.banky.service.CompteBancaireService;
import sn.modeltech.banky.service.dto.CompteBancaireDTO;
import sn.modeltech.banky.service.mapper.CompteBancaireMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.CompteBancaire}.
 */
@Service
@Transactional
public class CompteBancaireServiceImpl implements CompteBancaireService {

    private static final Logger LOG = LoggerFactory.getLogger(CompteBancaireServiceImpl.class);

    private final CompteBancaireRepository compteBancaireRepository;

    private final CompteBancaireMapper compteBancaireMapper;

    public CompteBancaireServiceImpl(CompteBancaireRepository compteBancaireRepository, CompteBancaireMapper compteBancaireMapper) {
        this.compteBancaireRepository = compteBancaireRepository;
        this.compteBancaireMapper = compteBancaireMapper;
    }

    @Override
    public CompteBancaireDTO save(CompteBancaireDTO compteBancaireDTO) {
        LOG.debug("Request to save CompteBancaire : {}", compteBancaireDTO);
        CompteBancaire compteBancaire = compteBancaireMapper.toEntity(compteBancaireDTO);
        compteBancaire = compteBancaireRepository.save(compteBancaire);
        return compteBancaireMapper.toDto(compteBancaire);
    }

    @Override
    public CompteBancaireDTO update(CompteBancaireDTO compteBancaireDTO) {
        LOG.debug("Request to update CompteBancaire : {}", compteBancaireDTO);
        CompteBancaire compteBancaire = compteBancaireMapper.toEntity(compteBancaireDTO);
        compteBancaire.setIsPersisted();
        compteBancaire = compteBancaireRepository.save(compteBancaire);
        return compteBancaireMapper.toDto(compteBancaire);
    }

    @Override
    public Optional<CompteBancaireDTO> partialUpdate(CompteBancaireDTO compteBancaireDTO) {
        LOG.debug("Request to partially update CompteBancaire : {}", compteBancaireDTO);

        return compteBancaireRepository
            .findById(compteBancaireDTO.getIdCompteBancaire())
            .map(existingCompteBancaire -> {
                compteBancaireMapper.partialUpdate(existingCompteBancaire, compteBancaireDTO);

                return existingCompteBancaire;
            })
            .map(compteBancaireRepository::save)
            .map(compteBancaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompteBancaireDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CompteBancaires");
        return compteBancaireRepository.findAll(pageable).map(compteBancaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompteBancaireDTO> findOne(String id) {
        LOG.debug("Request to get CompteBancaire : {}", id);
        return compteBancaireRepository.findById(id).map(compteBancaireMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete CompteBancaire : {}", id);
        compteBancaireRepository.deleteById(id);
    }
}
