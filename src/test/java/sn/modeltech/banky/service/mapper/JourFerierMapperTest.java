package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.JourFerierAsserts.*;
import static sn.modeltech.banky.domain.JourFerierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JourFerierMapperTest {

    private JourFerierMapper jourFerierMapper;

    @BeforeEach
    void setUp() {
        jourFerierMapper = new JourFerierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getJourFerierSample1();
        var actual = jourFerierMapper.toEntity(jourFerierMapper.toDto(expected));
        assertJourFerierAllPropertiesEquals(expected, actual);
    }
}
