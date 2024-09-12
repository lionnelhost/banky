package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.JourFerier;
import sn.modeltech.banky.service.dto.JourFerierDTO;

/**
 * Mapper for the entity {@link JourFerier} and its DTO {@link JourFerierDTO}.
 */
@Mapper(componentModel = "spring")
public interface JourFerierMapper extends EntityMapper<JourFerierDTO, JourFerier> {}
