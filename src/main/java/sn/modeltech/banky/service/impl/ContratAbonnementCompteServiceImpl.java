package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.ContratAbonnementCompte;
import sn.modeltech.banky.repository.ContratAbonnementCompteRepository;
import sn.modeltech.banky.service.ContratAbonnementCompteService;
import sn.modeltech.banky.service.dto.ContratAbonnementCompteDTO;
import sn.modeltech.banky.service.mapper.ContratAbonnementCompteMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.ContratAbonnementCompte}.
 */
@Service
@Transactional
public class ContratAbonnementCompteServiceImpl implements ContratAbonnementCompteService {

    private static final Logger LOG = LoggerFactory.getLogger(ContratAbonnementCompteServiceImpl.class);

    private final ContratAbonnementCompteRepository contratAbonnementCompteRepository;

    private final ContratAbonnementCompteMapper contratAbonnementCompteMapper;

    public ContratAbonnementCompteServiceImpl(
        ContratAbonnementCompteRepository contratAbonnementCompteRepository,
        ContratAbonnementCompteMapper contratAbonnementCompteMapper
    ) {
        this.contratAbonnementCompteRepository = contratAbonnementCompteRepository;
        this.contratAbonnementCompteMapper = contratAbonnementCompteMapper;
    }

    @Override
    public ContratAbonnementCompteDTO save(ContratAbonnementCompteDTO contratAbonnementCompteDTO) {
        LOG.debug("Request to save ContratAbonnementCompte : {}", contratAbonnementCompteDTO);
        ContratAbonnementCompte contratAbonnementCompte = contratAbonnementCompteMapper.toEntity(contratAbonnementCompteDTO);
        contratAbonnementCompte = contratAbonnementCompteRepository.save(contratAbonnementCompte);
        return contratAbonnementCompteMapper.toDto(contratAbonnementCompte);
    }

    @Override
    public ContratAbonnementCompteDTO update(ContratAbonnementCompteDTO contratAbonnementCompteDTO) {
        LOG.debug("Request to update ContratAbonnementCompte : {}", contratAbonnementCompteDTO);
        ContratAbonnementCompte contratAbonnementCompte = contratAbonnementCompteMapper.toEntity(contratAbonnementCompteDTO);
        contratAbonnementCompte = contratAbonnementCompteRepository.save(contratAbonnementCompte);
        return contratAbonnementCompteMapper.toDto(contratAbonnementCompte);
    }

    @Override
    public Optional<ContratAbonnementCompteDTO> partialUpdate(ContratAbonnementCompteDTO contratAbonnementCompteDTO) {
        LOG.debug("Request to partially update ContratAbonnementCompte : {}", contratAbonnementCompteDTO);

        return contratAbonnementCompteRepository
            .findById(contratAbonnementCompteDTO.getId())
            .map(existingContratAbonnementCompte -> {
                contratAbonnementCompteMapper.partialUpdate(existingContratAbonnementCompte, contratAbonnementCompteDTO);

                return existingContratAbonnementCompte;
            })
            .map(contratAbonnementCompteRepository::save)
            .map(contratAbonnementCompteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContratAbonnementCompteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ContratAbonnementComptes");
        return contratAbonnementCompteRepository.findAll(pageable).map(contratAbonnementCompteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContratAbonnementCompteDTO> findOne(Long id) {
        LOG.debug("Request to get ContratAbonnementCompte : {}", id);
        return contratAbonnementCompteRepository.findById(id).map(contratAbonnementCompteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ContratAbonnementCompte : {}", id);
        contratAbonnementCompteRepository.deleteById(id);
    }
}
