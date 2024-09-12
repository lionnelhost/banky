package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.Abonne;
import sn.modeltech.banky.repository.AbonneRepository;
import sn.modeltech.banky.service.AbonneService;
import sn.modeltech.banky.service.dto.AbonneDTO;
import sn.modeltech.banky.service.mapper.AbonneMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.Abonne}.
 */
@Service
@Transactional
public class AbonneServiceImpl implements AbonneService {

    private static final Logger LOG = LoggerFactory.getLogger(AbonneServiceImpl.class);

    private final AbonneRepository abonneRepository;

    private final AbonneMapper abonneMapper;

    public AbonneServiceImpl(AbonneRepository abonneRepository, AbonneMapper abonneMapper) {
        this.abonneRepository = abonneRepository;
        this.abonneMapper = abonneMapper;
    }

    @Override
    public AbonneDTO save(AbonneDTO abonneDTO) {
        LOG.debug("Request to save Abonne : {}", abonneDTO);
        Abonne abonne = abonneMapper.toEntity(abonneDTO);
        abonne = abonneRepository.save(abonne);
        return abonneMapper.toDto(abonne);
    }

    @Override
    public AbonneDTO update(AbonneDTO abonneDTO) {
        LOG.debug("Request to update Abonne : {}", abonneDTO);
        Abonne abonne = abonneMapper.toEntity(abonneDTO);
        abonne.setIsPersisted();
        abonne = abonneRepository.save(abonne);
        return abonneMapper.toDto(abonne);
    }

    @Override
    public Optional<AbonneDTO> partialUpdate(AbonneDTO abonneDTO) {
        LOG.debug("Request to partially update Abonne : {}", abonneDTO);

        return abonneRepository
            .findById(abonneDTO.getIdAbonne())
            .map(existingAbonne -> {
                abonneMapper.partialUpdate(existingAbonne, abonneDTO);

                return existingAbonne;
            })
            .map(abonneRepository::save)
            .map(abonneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbonneDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Abonnes");
        return abonneRepository.findAll(pageable).map(abonneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbonneDTO> findOne(String id) {
        LOG.debug("Request to get Abonne : {}", id);
        return abonneRepository.findById(id).map(abonneMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Abonne : {}", id);
        abonneRepository.deleteById(id);
    }
}
