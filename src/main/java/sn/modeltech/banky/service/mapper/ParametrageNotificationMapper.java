package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.ParametrageNotification;
import sn.modeltech.banky.service.dto.ParametrageNotificationDTO;

/**
 * Mapper for the entity {@link ParametrageNotification} and its DTO {@link ParametrageNotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParametrageNotificationMapper extends EntityMapper<ParametrageNotificationDTO, ParametrageNotification> {}
