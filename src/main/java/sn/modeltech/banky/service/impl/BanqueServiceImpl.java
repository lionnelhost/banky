package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.Banque;
import sn.modeltech.banky.repository.BanqueRepository;
import sn.modeltech.banky.service.BanqueService;
import sn.modeltech.banky.service.dto.BanqueDTO;
import sn.modeltech.banky.service.mapper.BanqueMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.Banque}.
 */
@Service
@Transactional
public class BanqueServiceImpl implements BanqueService {

    private static final Logger LOG = LoggerFactory.getLogger(BanqueServiceImpl.class);

    private final BanqueRepository banqueRepository;

    private final BanqueMapper banqueMapper;

    public BanqueServiceImpl(BanqueRepository banqueRepository, BanqueMapper banqueMapper) {
        this.banqueRepository = banqueRepository;
        this.banqueMapper = banqueMapper;
    }

    @Override
    public BanqueDTO save(BanqueDTO banqueDTO) {
        LOG.debug("Request to save Banque : {}", banqueDTO);
        Banque banque = banqueMapper.toEntity(banqueDTO);
        banque = banqueRepository.save(banque);
        return banqueMapper.toDto(banque);
    }

    @Override
    public BanqueDTO update(BanqueDTO banqueDTO) {
        LOG.debug("Request to update Banque : {}", banqueDTO);
        Banque banque = banqueMapper.toEntity(banqueDTO);
        banque.setIsPersisted();
        banque = banqueRepository.save(banque);
        return banqueMapper.toDto(banque);
    }

    @Override
    public Optional<BanqueDTO> partialUpdate(BanqueDTO banqueDTO) {
        LOG.debug("Request to partially update Banque : {}", banqueDTO);

        return banqueRepository
            .findById(banqueDTO.getIdBanque())
            .map(existingBanque -> {
                banqueMapper.partialUpdate(existingBanque, banqueDTO);

                return existingBanque;
            })
            .map(banqueRepository::save)
            .map(banqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BanqueDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Banques");
        return banqueRepository.findAll(pageable).map(banqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BanqueDTO> findOne(String id) {
        LOG.debug("Request to get Banque : {}", id);
        return banqueRepository.findById(id).map(banqueMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Banque : {}", id);
        banqueRepository.deleteById(id);
    }
}
