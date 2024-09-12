package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.AgenceAsserts.*;
import static sn.modeltech.banky.domain.AgenceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgenceMapperTest {

    private AgenceMapper agenceMapper;

    @BeforeEach
    void setUp() {
        agenceMapper = new AgenceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAgenceSample1();
        var actual = agenceMapper.toEntity(agenceMapper.toDto(expected));
        assertAgenceAllPropertiesEquals(expected, actual);
    }
}
