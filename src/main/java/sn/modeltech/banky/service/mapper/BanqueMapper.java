package sn.modeltech.banky.service.mapper;

import org.mapstruct.*;
import sn.modeltech.banky.domain.Banque;
import sn.modeltech.banky.service.dto.BanqueDTO;

/**
 * Mapper for the entity {@link Banque} and its DTO {@link BanqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface BanqueMapper extends EntityMapper<BanqueDTO, Banque> {}
