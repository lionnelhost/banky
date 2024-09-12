package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.CanalAsserts.*;
import static sn.modeltech.banky.domain.CanalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CanalMapperTest {

    private CanalMapper canalMapper;

    @BeforeEach
    void setUp() {
        canalMapper = new CanalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCanalSample1();
        var actual = canalMapper.toEntity(canalMapper.toDto(expected));
        assertCanalAllPropertiesEquals(expected, actual);
    }
}
