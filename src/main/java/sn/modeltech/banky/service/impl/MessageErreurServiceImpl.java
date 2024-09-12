package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.MessageErreur;
import sn.modeltech.banky.repository.MessageErreurRepository;
import sn.modeltech.banky.service.MessageErreurService;
import sn.modeltech.banky.service.dto.MessageErreurDTO;
import sn.modeltech.banky.service.mapper.MessageErreurMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.MessageErreur}.
 */
@Service
@Transactional
public class MessageErreurServiceImpl implements MessageErreurService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageErreurServiceImpl.class);

    private final MessageErreurRepository messageErreurRepository;

    private final MessageErreurMapper messageErreurMapper;

    public MessageErreurServiceImpl(MessageErreurRepository messageErreurRepository, MessageErreurMapper messageErreurMapper) {
        this.messageErreurRepository = messageErreurRepository;
        this.messageErreurMapper = messageErreurMapper;
    }

    @Override
    public MessageErreurDTO save(MessageErreurDTO messageErreurDTO) {
        LOG.debug("Request to save MessageErreur : {}", messageErreurDTO);
        MessageErreur messageErreur = messageErreurMapper.toEntity(messageErreurDTO);
        messageErreur = messageErreurRepository.save(messageErreur);
        return messageErreurMapper.toDto(messageErreur);
    }

    @Override
    public MessageErreurDTO update(MessageErreurDTO messageErreurDTO) {
        LOG.debug("Request to update MessageErreur : {}", messageErreurDTO);
        MessageErreur messageErreur = messageErreurMapper.toEntity(messageErreurDTO);
        messageErreur.setIsPersisted();
        messageErreur = messageErreurRepository.save(messageErreur);
        return messageErreurMapper.toDto(messageErreur);
    }

    @Override
    public Optional<MessageErreurDTO> partialUpdate(MessageErreurDTO messageErreurDTO) {
        LOG.debug("Request to partially update MessageErreur : {}", messageErreurDTO);

        return messageErreurRepository
            .findById(messageErreurDTO.getIdMessageErreur())
            .map(existingMessageErreur -> {
                messageErreurMapper.partialUpdate(existingMessageErreur, messageErreurDTO);

                return existingMessageErreur;
            })
            .map(messageErreurRepository::save)
            .map(messageErreurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageErreurDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MessageErreurs");
        return messageErreurRepository.findAll(pageable).map(messageErreurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageErreurDTO> findOne(String id) {
        LOG.debug("Request to get MessageErreur : {}", id);
        return messageErreurRepository.findById(id).map(messageErreurMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete MessageErreur : {}", id);
        messageErreurRepository.deleteById(id);
    }
}
