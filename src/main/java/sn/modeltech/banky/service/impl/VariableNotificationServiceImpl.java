package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.VariableNotification;
import sn.modeltech.banky.repository.VariableNotificationRepository;
import sn.modeltech.banky.service.VariableNotificationService;
import sn.modeltech.banky.service.dto.VariableNotificationDTO;
import sn.modeltech.banky.service.mapper.VariableNotificationMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.VariableNotification}.
 */
@Service
@Transactional
public class VariableNotificationServiceImpl implements VariableNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(VariableNotificationServiceImpl.class);

    private final VariableNotificationRepository variableNotificationRepository;

    private final VariableNotificationMapper variableNotificationMapper;

    public VariableNotificationServiceImpl(
        VariableNotificationRepository variableNotificationRepository,
        VariableNotificationMapper variableNotificationMapper
    ) {
        this.variableNotificationRepository = variableNotificationRepository;
        this.variableNotificationMapper = variableNotificationMapper;
    }

    @Override
    public VariableNotificationDTO save(VariableNotificationDTO variableNotificationDTO) {
        LOG.debug("Request to save VariableNotification : {}", variableNotificationDTO);
        VariableNotification variableNotification = variableNotificationMapper.toEntity(variableNotificationDTO);
        variableNotification = variableNotificationRepository.save(variableNotification);
        return variableNotificationMapper.toDto(variableNotification);
    }

    @Override
    public VariableNotificationDTO update(VariableNotificationDTO variableNotificationDTO) {
        LOG.debug("Request to update VariableNotification : {}", variableNotificationDTO);
        VariableNotification variableNotification = variableNotificationMapper.toEntity(variableNotificationDTO);
        variableNotification.setIsPersisted();
        variableNotification = variableNotificationRepository.save(variableNotification);
        return variableNotificationMapper.toDto(variableNotification);
    }

    @Override
    public Optional<VariableNotificationDTO> partialUpdate(VariableNotificationDTO variableNotificationDTO) {
        LOG.debug("Request to partially update VariableNotification : {}", variableNotificationDTO);

        return variableNotificationRepository
            .findById(variableNotificationDTO.getIdVarNotification())
            .map(existingVariableNotification -> {
                variableNotificationMapper.partialUpdate(existingVariableNotification, variableNotificationDTO);

                return existingVariableNotification;
            })
            .map(variableNotificationRepository::save)
            .map(variableNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VariableNotificationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all VariableNotifications");
        return variableNotificationRepository.findAll(pageable).map(variableNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VariableNotificationDTO> findOne(String id) {
        LOG.debug("Request to get VariableNotification : {}", id);
        return variableNotificationRepository.findById(id).map(variableNotificationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete VariableNotification : {}", id);
        variableNotificationRepository.deleteById(id);
    }
}
