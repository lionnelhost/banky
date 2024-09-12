package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.DispositifSignatureAsserts.*;
import static sn.modeltech.banky.domain.DispositifSignatureTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositifSignatureMapperTest {

    private DispositifSignatureMapper dispositifSignatureMapper;

    @BeforeEach
    void setUp() {
        dispositifSignatureMapper = new DispositifSignatureMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDispositifSignatureSample1();
        var actual = dispositifSignatureMapper.toEntity(dispositifSignatureMapper.toDto(expected));
        assertDispositifSignatureAllPropertiesEquals(expected, actual);
    }
}
