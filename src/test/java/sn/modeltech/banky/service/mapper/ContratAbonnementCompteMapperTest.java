package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.ContratAbonnementCompteAsserts.*;
import static sn.modeltech.banky.domain.ContratAbonnementCompteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContratAbonnementCompteMapperTest {

    private ContratAbonnementCompteMapper contratAbonnementCompteMapper;

    @BeforeEach
    void setUp() {
        contratAbonnementCompteMapper = new ContratAbonnementCompteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContratAbonnementCompteSample1();
        var actual = contratAbonnementCompteMapper.toEntity(contratAbonnementCompteMapper.toDto(expected));
        assertContratAbonnementCompteAllPropertiesEquals(expected, actual);
    }
}
