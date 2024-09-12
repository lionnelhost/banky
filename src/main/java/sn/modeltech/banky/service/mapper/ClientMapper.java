package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Client;
import sn.modeltech.banky.domain.Contrat;
import sn.modeltech.banky.domain.TypeClient;
import sn.modeltech.banky.service.dto.ClientDTO;
import sn.modeltech.banky.service.dto.ContratDTO;
import sn.modeltech.banky.service.dto.TypeClientDTO;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "contrat", source = "contrat", qualifiedByName = "contratIdContrat")
    @Mapping(target = "typeClient", source = "typeClient", qualifiedByName = "typeClientIdTypeClient")
    ClientDTO toDto(Client s);

    @Named("contratIdContrat")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idContrat", source = "idContrat")
    ContratDTO toDtoContratIdContrat(Contrat contrat);

    @Named("typeClientIdTypeClient")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idTypeClient", source = "idTypeClient")
    TypeClientDTO toDtoTypeClientIdTypeClient(TypeClient typeClient);
}
