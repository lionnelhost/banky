package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.TypeTransaction;
import sn.modeltech.banky.service.dto.TypeTransactionDTO;

/**
 * Mapper for the entity {@link TypeTransaction} and its DTO {@link TypeTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeTransactionMapper extends EntityMapper<TypeTransactionDTO, TypeTransaction> {}
