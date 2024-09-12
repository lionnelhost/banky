package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.ContratAbonnementAsserts.*;
import static sn.modeltech.banky.domain.ContratAbonnementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContratAbonnementMapperTest {

    private ContratAbonnementMapper contratAbonnementMapper;

    @BeforeEach
    void setUp() {
        contratAbonnementMapper = new ContratAbonnementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContratAbonnementSample1();
        var actual = contratAbonnementMapper.toEntity(contratAbonnementMapper.toDto(expected));
        assertContratAbonnementAllPropertiesEquals(expected, actual);
    }
}
