package sn.modeltech.banky.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.modeltech.banky.domain.Participant;
import sn.modeltech.banky.repository.ParticipantRepository;
import sn.modeltech.banky.service.ParticipantService;
import sn.modeltech.banky.service.dto.ParticipantDTO;
import sn.modeltech.banky.service.mapper.ParticipantMapper;

/**
 * Service Implementation for managing {@link sn.modeltech.banky.domain.Participant}.
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private static final Logger LOG = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    private final ParticipantRepository participantRepository;

    private final ParticipantMapper participantMapper;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    @Override
    public ParticipantDTO save(ParticipantDTO participantDTO) {
        LOG.debug("Request to save Participant : {}", participantDTO);
        Participant participant = participantMapper.toEntity(participantDTO);
        participant = participantRepository.save(participant);
        return participantMapper.toDto(participant);
    }

    @Override
    public ParticipantDTO update(ParticipantDTO participantDTO) {
        LOG.debug("Request to update Participant : {}", participantDTO);
        Participant participant = participantMapper.toEntity(participantDTO);
        participant.setIsPersisted();
        participant = participantRepository.save(participant);
        return participantMapper.toDto(participant);
    }

    @Override
    public Optional<ParticipantDTO> partialUpdate(ParticipantDTO participantDTO) {
        LOG.debug("Request to partially update Participant : {}", participantDTO);

        return participantRepository
            .findById(participantDTO.getIdParticipant())
            .map(existingParticipant -> {
                participantMapper.partialUpdate(existingParticipant, participantDTO);

                return existingParticipant;
            })
            .map(participantRepository::save)
            .map(participantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParticipantDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Participants");
        return participantRepository.findAll(pageable).map(participantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantDTO> findOne(String id) {
        LOG.debug("Request to get Participant : {}", id);
        return participantRepository.findById(id).map(participantMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Participant : {}", id);
        participantRepository.deleteById(id);
    }
}
