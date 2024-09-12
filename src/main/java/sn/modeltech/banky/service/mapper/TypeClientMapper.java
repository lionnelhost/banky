package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.TypeClient;
import sn.modeltech.banky.service.dto.TypeClientDTO;

/**
 * Mapper for the entity {@link TypeClient} and its DTO {@link TypeClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeClientMapper extends EntityMapper<TypeClientDTO, TypeClient> {}
