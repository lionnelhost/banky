package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.AbonneAsserts.*;
import static sn.modeltech.banky.domain.AbonneTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbonneMapperTest {

    private AbonneMapper abonneMapper;

    @BeforeEach
    void setUp() {
        abonneMapper = new AbonneMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAbonneSample1();
        var actual = abonneMapper.toEntity(abonneMapper.toDto(expected));
        assertAbonneAllPropertiesEquals(expected, actual);
    }
}
