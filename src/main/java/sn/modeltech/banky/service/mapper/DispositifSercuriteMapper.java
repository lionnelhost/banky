package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Canal;
import sn.modeltech.banky.domain.DispositifSercurite;
import sn.modeltech.banky.domain.DispositifSignature;
import sn.modeltech.banky.domain.TypeTransaction;
import sn.modeltech.banky.service.dto.CanalDTO;
import sn.modeltech.banky.service.dto.DispositifSercuriteDTO;
import sn.modeltech.banky.service.dto.DispositifSignatureDTO;
import sn.modeltech.banky.service.dto.TypeTransactionDTO;

/**
 * Mapper for the entity {@link DispositifSercurite} and its DTO {@link DispositifSercuriteDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositifSercuriteMapper extends EntityMapper<DispositifSercuriteDTO, DispositifSercurite> {
    @Mapping(target = "canal", source = "canal", qualifiedByName = "canalIdCanal")
    @Mapping(target = "typeTransaction", source = "typeTransaction", qualifiedByName = "typeTransactionIdTypeTransaction")
    @Mapping(target = "dispositifSignature", source = "dispositifSignature", qualifiedByName = "dispositifSignatureIdDispositif")
    DispositifSercuriteDTO toDto(DispositifSercurite s);

    @Named("canalIdCanal")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCanal", source = "idCanal")
    CanalDTO toDtoCanalIdCanal(Canal canal);

    @Named("typeTransactionIdTypeTransaction")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idTypeTransaction", source = "idTypeTransaction")
    TypeTransactionDTO toDtoTypeTransactionIdTypeTransaction(TypeTransaction typeTransaction);

    @Named("dispositifSignatureIdDispositif")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idDispositif", source = "idDispositif")
    DispositifSignatureDTO toDtoDispositifSignatureIdDispositif(DispositifSignature dispositifSignature);
}
