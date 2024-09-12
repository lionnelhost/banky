package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.CompteBancaire;
import sn.modeltech.banky.domain.Contrat;
import sn.modeltech.banky.service.dto.CompteBancaireDTO;
import sn.modeltech.banky.service.dto.ContratDTO;

/**
 * Mapper for the entity {@link CompteBancaire} and its DTO {@link CompteBancaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompteBancaireMapper extends EntityMapper<CompteBancaireDTO, CompteBancaire> {
    @Mapping(target = "contrat", source = "contrat", qualifiedByName = "contratIdContrat")
    CompteBancaireDTO toDto(CompteBancaire s);

    @Named("contratIdContrat")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idContrat", source = "idContrat")
    ContratDTO toDtoContratIdContrat(Contrat contrat);
}
