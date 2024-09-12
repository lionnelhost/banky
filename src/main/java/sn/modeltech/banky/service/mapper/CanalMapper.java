package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Canal;
import sn.modeltech.banky.service.dto.CanalDTO;

/**
 * Mapper for the entity {@link Canal} and its DTO {@link CanalDTO}.
 */
@Mapper(componentModel = "spring")
public interface CanalMapper extends EntityMapper<CanalDTO, Canal> {}
