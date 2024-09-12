package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.ParametrageGlobal;
import sn.modeltech.banky.service.dto.ParametrageGlobalDTO;

/**
 * Mapper for the entity {@link ParametrageGlobal} and its DTO {@link ParametrageGlobalDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParametrageGlobalMapper extends EntityMapper<ParametrageGlobalDTO, ParametrageGlobal> {}
