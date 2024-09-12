package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.ContratAbonnement;
import sn.modeltech.banky.repository.ContratAbonnementRepository;
import sn.modeltech.banky.service.ContratAbonnementService;
import sn.modeltech.banky.service.dto.ContratAbonnementDTO;
import sn.modeltech.banky.service.mapper.ContratAbonnementMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.ContratAbonnement}.
 */
@Service
@Transactional
public class ContratAbonnementServiceImpl implements ContratAbonnementService {

    private static final Logger LOG = LoggerFactory.getLogger(ContratAbonnementServiceImpl.class);

    private final ContratAbonnementRepository contratAbonnementRepository;

    private final ContratAbonnementMapper contratAbonnementMapper;

    public ContratAbonnementServiceImpl(
        ContratAbonnementRepository contratAbonnementRepository,
        ContratAbonnementMapper contratAbonnementMapper
    ) {
        this.contratAbonnementRepository = contratAbonnementRepository;
        this.contratAbonnementMapper = contratAbonnementMapper;
    }

    @Override
    public ContratAbonnementDTO save(ContratAbonnementDTO contratAbonnementDTO) {
        LOG.debug("Request to save ContratAbonnement : {}", contratAbonnementDTO);
        ContratAbonnement contratAbonnement = contratAbonnementMapper.toEntity(contratAbonnementDTO);
        contratAbonnement = contratAbonnementRepository.save(contratAbonnement);
        return contratAbonnementMapper.toDto(contratAbonnement);
    }

    @Override
    public ContratAbonnementDTO update(ContratAbonnementDTO contratAbonnementDTO) {
        LOG.debug("Request to update ContratAbonnement : {}", contratAbonnementDTO);
        ContratAbonnement contratAbonnement = contratAbonnementMapper.toEntity(contratAbonnementDTO);
        contratAbonnement = contratAbonnementRepository.save(contratAbonnement);
        return contratAbonnementMapper.toDto(contratAbonnement);
    }

    @Override
    public Optional<ContratAbonnementDTO> partialUpdate(ContratAbonnementDTO contratAbonnementDTO) {
        LOG.debug("Request to partially update ContratAbonnement : {}", contratAbonnementDTO);

        return contratAbonnementRepository
            .findById(contratAbonnementDTO.getId())
            .map(existingContratAbonnement -> {
                contratAbonnementMapper.partialUpdate(existingContratAbonnement, contratAbonnementDTO);

                return existingContratAbonnement;
            })
            .map(contratAbonnementRepository::save)
            .map(contratAbonnementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContratAbonnementDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ContratAbonnements");
        return contratAbonnementRepository.findAll(pageable).map(contratAbonnementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContratAbonnementDTO> findOne(Long id) {
        LOG.debug("Request to get ContratAbonnement : {}", id);
        return contratAbonnementRepository.findById(id).map(contratAbonnementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ContratAbonnement : {}", id);
        contratAbonnementRepository.deleteById(id);
    }
}
