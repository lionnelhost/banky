package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.ParametrageNotificationAsserts.*;
import static sn.modeltech.banky.domain.ParametrageNotificationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParametrageNotificationMapperTest {

    private ParametrageNotificationMapper parametrageNotificationMapper;

    @BeforeEach
    void setUp() {
        parametrageNotificationMapper = new ParametrageNotificationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getParametrageNotificationSample1();
        var actual = parametrageNotificationMapper.toEntity(parametrageNotificationMapper.toDto(expected));
        assertParametrageNotificationAllPropertiesEquals(expected, actual);
    }
}
