package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Abonne;
import sn.modeltech.banky.service.dto.AbonneDTO;

/**
 * Mapper for the entity {@link Abonne} and its DTO {@link AbonneDTO}.
 */
@Mapper(componentModel = "spring")
public interface AbonneMapper extends EntityMapper<AbonneDTO, Abonne> {}
