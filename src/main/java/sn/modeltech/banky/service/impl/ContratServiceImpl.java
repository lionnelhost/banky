package sn.modeltech.banky.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.Contrat;
import sn.modeltech.banky.repository.ContratRepository;
import sn.modeltech.banky.service.ContratService;
import sn.modeltech.banky.service.dto.ContratDTO;
import sn.modeltech.banky.service.mapper.ContratMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.Contrat}.
 */
@Service
@Transactional
public class ContratServiceImpl implements ContratService {

    private static final Logger LOG = LoggerFactory.getLogger(ContratServiceImpl.class);

    private final ContratRepository contratRepository;

    private final ContratMapper contratMapper;

    public ContratServiceImpl(ContratRepository contratRepository, ContratMapper contratMapper) {
        this.contratRepository = contratRepository;
        this.contratMapper = contratMapper;
    }

    @Override
    public ContratDTO save(ContratDTO contratDTO) {
        LOG.debug("Request to save Contrat : {}", contratDTO);
        Contrat contrat = contratMapper.toEntity(contratDTO);
        contrat = contratRepository.save(contrat);
        return contratMapper.toDto(contrat);
    }

    @Override
    public ContratDTO update(ContratDTO contratDTO) {
        LOG.debug("Request to update Contrat : {}", contratDTO);
        Contrat contrat = contratMapper.toEntity(contratDTO);
        contrat.setIsPersisted();
        contrat = contratRepository.save(contrat);
        return contratMapper.toDto(contrat);
    }

    @Override
    public Optional<ContratDTO> partialUpdate(ContratDTO contratDTO) {
        LOG.debug("Request to partially update Contrat : {}", contratDTO);

        return contratRepository
            .findById(contratDTO.getIdContrat())
            .map(existingContrat -> {
                contratMapper.partialUpdate(existingContrat, contratDTO);

                return existingContrat;
            })
            .map(contratRepository::save)
            .map(contratMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContratDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Contrats");
        return contratRepository.findAll(pageable).map(contratMapper::toDto);
    }

    /**
     *  Get all the contrats where Client is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContratDTO> findAllWhereClientIsNull() {
        LOG.debug("Request to get all contrats where Client is null");
        return StreamSupport.stream(contratRepository.findAll().spliterator(), false)
            .filter(contrat -> contrat.getClient() == null)
            .map(contratMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContratDTO> findOne(String id) {
        LOG.debug("Request to get Contrat : {}", id);
        return contratRepository.findById(id).map(contratMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Contrat : {}", id);
        contratRepository.deleteById(id);
    }
}
