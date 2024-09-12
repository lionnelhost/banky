package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Contrat;
import sn.modeltech.banky.domain.TypeContrat;
import sn.modeltech.banky.service.dto.ContratDTO;
import sn.modeltech.banky.service.dto.TypeContratDTO;

/**
 * Mapper for the entity {@link Contrat} and its DTO {@link ContratDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContratMapper extends EntityMapper<ContratDTO, Contrat> {
    @Mapping(target = "typeContrat", source = "typeContrat", qualifiedByName = "typeContratIdTypeContrat")
    ContratDTO toDto(Contrat s);

    @Named("typeContratIdTypeContrat")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idTypeContrat", source = "idTypeContrat")
    TypeContratDTO toDtoTypeContratIdTypeContrat(TypeContrat typeContrat);
}
