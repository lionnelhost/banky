package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Abonne;
import sn.modeltech.banky.domain.CompteBancaire;
import sn.modeltech.banky.domain.Contrat;
import sn.modeltech.banky.domain.ContratAbonnementCompte;
import sn.modeltech.banky.service.dto.AbonneDTO;
import sn.modeltech.banky.service.dto.CompteBancaireDTO;
import sn.modeltech.banky.service.dto.ContratAbonnementCompteDTO;
import sn.modeltech.banky.service.dto.ContratDTO;

/**
 * Mapper for the entity {@link ContratAbonnementCompte} and its DTO {@link ContratAbonnementCompteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContratAbonnementCompteMapper extends EntityMapper<ContratAbonnementCompteDTO, ContratAbonnementCompte> {
    @Mapping(target = "contrat", source = "contrat", qualifiedByName = "contratIdContrat")
    @Mapping(target = "abonne", source = "abonne", qualifiedByName = "abonneIdAbonne")
    @Mapping(target = "compteBancaire", source = "compteBancaire", qualifiedByName = "compteBancaireIdCompteBancaire")
    ContratAbonnementCompteDTO toDto(ContratAbonnementCompte s);

    @Named("contratIdContrat")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idContrat", source = "idContrat")
    ContratDTO toDtoContratIdContrat(Contrat contrat);

    @Named("abonneIdAbonne")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idAbonne", source = "idAbonne")
    AbonneDTO toDtoAbonneIdAbonne(Abonne abonne);

    @Named("compteBancaireIdCompteBancaire")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCompteBancaire", source = "idCompteBancaire")
    CompteBancaireDTO toDtoCompteBancaireIdCompteBancaire(CompteBancaire compteBancaire);
}
