package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.ContratAsserts.*;
import static sn.modeltech.banky.domain.ContratTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContratMapperTest {

    private ContratMapper contratMapper;

    @BeforeEach
    void setUp() {
        contratMapper = new ContratMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContratSample1();
        var actual = contratMapper.toEntity(contratMapper.toDto(expected));
        assertContratAllPropertiesEquals(expected, actual);
    }
}
