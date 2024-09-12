package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.VariableNotification;
import sn.modeltech.banky.service.dto.VariableNotificationDTO;

/**
 * Mapper for the entity {@link VariableNotification} and its DTO {@link VariableNotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface VariableNotificationMapper extends EntityMapper<VariableNotificationDTO, VariableNotification> {}
