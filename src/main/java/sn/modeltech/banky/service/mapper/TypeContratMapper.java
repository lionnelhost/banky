package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.TypeContrat;
import sn.modeltech.banky.service.dto.TypeContratDTO;

/**
 * Mapper for the entity {@link TypeContrat} and its DTO {@link TypeContratDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeContratMapper extends EntityMapper<TypeContratDTO, TypeContrat> {}
