package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.VariableNotificationAsserts.*;
import static sn.modeltech.banky.domain.VariableNotificationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableNotificationMapperTest {

    private VariableNotificationMapper variableNotificationMapper;

    @BeforeEach
    void setUp() {
        variableNotificationMapper = new VariableNotificationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVariableNotificationSample1();
        var actual = variableNotificationMapper.toEntity(variableNotificationMapper.toDto(expected));
        assertVariableNotificationAllPropertiesEquals(expected, actual);
    }
}
