package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.DispositifSignature;
import sn.modeltech.banky.service.dto.DispositifSignatureDTO;

/**
 * Mapper for the entity {@link DispositifSignature} and its DTO {@link DispositifSignatureDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositifSignatureMapper extends EntityMapper<DispositifSignatureDTO, DispositifSignature> {}
