package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.ParametrageGlobalAsserts.*;
import static sn.modeltech.banky.domain.ParametrageGlobalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParametrageGlobalMapperTest {

    private ParametrageGlobalMapper parametrageGlobalMapper;

    @BeforeEach
    void setUp() {
        parametrageGlobalMapper = new ParametrageGlobalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getParametrageGlobalSample1();
        var actual = parametrageGlobalMapper.toEntity(parametrageGlobalMapper.toDto(expected));
        assertParametrageGlobalAllPropertiesEquals(expected, actual);
    }
}
