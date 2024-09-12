package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.TypeContratAsserts.*;
import static sn.modeltech.banky.domain.TypeContratTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeContratMapperTest {

    private TypeContratMapper typeContratMapper;

    @BeforeEach
    void setUp() {
        typeContratMapper = new TypeContratMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTypeContratSample1();
        var actual = typeContratMapper.toEntity(typeContratMapper.toDto(expected));
        assertTypeContratAllPropertiesEquals(expected, actual);
    }
}
