package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.ParametrageNotification;
import sn.modeltech.banky.repository.ParametrageNotificationRepository;
import sn.modeltech.banky.service.ParametrageNotificationService;
import sn.modeltech.banky.service.dto.ParametrageNotificationDTO;
import sn.modeltech.banky.service.mapper.ParametrageNotificationMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.ParametrageNotification}.
 */
@Service
@Transactional
public class ParametrageNotificationServiceImpl implements ParametrageNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(ParametrageNotificationServiceImpl.class);

    private final ParametrageNotificationRepository parametrageNotificationRepository;

    private final ParametrageNotificationMapper parametrageNotificationMapper;

    public ParametrageNotificationServiceImpl(
        ParametrageNotificationRepository parametrageNotificationRepository,
        ParametrageNotificationMapper parametrageNotificationMapper
    ) {
        this.parametrageNotificationRepository = parametrageNotificationRepository;
        this.parametrageNotificationMapper = parametrageNotificationMapper;
    }

    @Override
    public ParametrageNotificationDTO save(ParametrageNotificationDTO parametrageNotificationDTO) {
        LOG.debug("Request to save ParametrageNotification : {}", parametrageNotificationDTO);
        ParametrageNotification parametrageNotification = parametrageNotificationMapper.toEntity(parametrageNotificationDTO);
        parametrageNotification = parametrageNotificationRepository.save(parametrageNotification);
        return parametrageNotificationMapper.toDto(parametrageNotification);
    }

    @Override
    public ParametrageNotificationDTO update(ParametrageNotificationDTO parametrageNotificationDTO) {
        LOG.debug("Request to update ParametrageNotification : {}", parametrageNotificationDTO);
        ParametrageNotification parametrageNotification = parametrageNotificationMapper.toEntity(parametrageNotificationDTO);
        parametrageNotification.setIsPersisted();
        parametrageNotification = parametrageNotificationRepository.save(parametrageNotification);
        return parametrageNotificationMapper.toDto(parametrageNotification);
    }

    @Override
    public Optional<ParametrageNotificationDTO> partialUpdate(ParametrageNotificationDTO parametrageNotificationDTO) {
        LOG.debug("Request to partially update ParametrageNotification : {}", parametrageNotificationDTO);

        return parametrageNotificationRepository
            .findById(parametrageNotificationDTO.getIdParamNotif())
            .map(existingParametrageNotification -> {
                parametrageNotificationMapper.partialUpdate(existingParametrageNotification, parametrageNotificationDTO);

                return existingParametrageNotification;
            })
            .map(parametrageNotificationRepository::save)
            .map(parametrageNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParametrageNotificationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ParametrageNotifications");
        return parametrageNotificationRepository.findAll(pageable).map(parametrageNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParametrageNotificationDTO> findOne(String id) {
        LOG.debug("Request to get ParametrageNotification : {}", id);
        return parametrageNotificationRepository.findById(id).map(parametrageNotificationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete ParametrageNotification : {}", id);
        parametrageNotificationRepository.deleteById(id);
    }
}
