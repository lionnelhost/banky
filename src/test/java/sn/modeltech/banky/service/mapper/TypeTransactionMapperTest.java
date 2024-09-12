package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.TypeTransactionAsserts.*;
import static sn.modeltech.banky.domain.TypeTransactionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeTransactionMapperTest {

    private TypeTransactionMapper typeTransactionMapper;

    @BeforeEach
    void setUp() {
        typeTransactionMapper = new TypeTransactionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTypeTransactionSample1();
        var actual = typeTransactionMapper.toEntity(typeTransactionMapper.toDto(expected));
        assertTypeTransactionAllPropertiesEquals(expected, actual);
    }
}
