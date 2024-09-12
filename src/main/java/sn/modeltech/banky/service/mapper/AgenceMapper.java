package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Agence;
import sn.modeltech.banky.domain.Banque;
import sn.modeltech.banky.service.dto.AgenceDTO;
import sn.modeltech.banky.service.dto.BanqueDTO;

/**
 * Mapper for the entity {@link Agence} and its DTO {@link AgenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgenceMapper extends EntityMapper<AgenceDTO, Agence> {
    @Mapping(target = "banque", source = "banque", qualifiedByName = "banqueIdBanque")
    AgenceDTO toDto(Agence s);

    @Named("banqueIdBanque")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idBanque", source = "idBanque")
    BanqueDTO toDtoBanqueIdBanque(Banque banque);
}
