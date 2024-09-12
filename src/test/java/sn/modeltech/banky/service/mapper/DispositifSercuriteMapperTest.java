package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.DispositifSercuriteAsserts.*;
import static sn.modeltech.banky.domain.DispositifSercuriteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositifSercuriteMapperTest {

    private DispositifSercuriteMapper dispositifSercuriteMapper;

    @BeforeEach
    void setUp() {
        dispositifSercuriteMapper = new DispositifSercuriteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDispositifSercuriteSample1();
        var actual = dispositifSercuriteMapper.toEntity(dispositifSercuriteMapper.toDto(expected));
        assertDispositifSercuriteAllPropertiesEquals(expected, actual);
    }
}
