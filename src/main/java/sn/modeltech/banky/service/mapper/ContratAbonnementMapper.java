package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Abonne;
import sn.modeltech.banky.domain.Contrat;
import sn.modeltech.banky.domain.ContratAbonnement;
import sn.modeltech.banky.service.dto.AbonneDTO;
import sn.modeltech.banky.service.dto.ContratAbonnementDTO;
import sn.modeltech.banky.service.dto.ContratDTO;

/**
 * Mapper for the entity {@link ContratAbonnement} and its DTO {@link ContratAbonnementDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContratAbonnementMapper extends EntityMapper<ContratAbonnementDTO, ContratAbonnement> {
    @Mapping(target = "contrat", source = "contrat", qualifiedByName = "contratIdContrat")
    @Mapping(target = "abonne", source = "abonne", qualifiedByName = "abonneIdAbonne")
    ContratAbonnementDTO toDto(ContratAbonnement s);

    @Named("contratIdContrat")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idContrat", source = "idContrat")
    ContratDTO toDtoContratIdContrat(Contrat contrat);

    @Named("abonneIdAbonne")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idAbonne", source = "idAbonne")
    AbonneDTO toDtoAbonneIdAbonne(Abonne abonne);
}
