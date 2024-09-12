package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.MessageErreur;
import sn.modeltech.banky.service.dto.MessageErreurDTO;

/**
 * Mapper for the entity {@link MessageErreur} and its DTO {@link MessageErreurDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageErreurMapper extends EntityMapper<MessageErreurDTO, MessageErreur> {}
