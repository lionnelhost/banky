package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.CompteBancaireAsserts.*;
import static sn.modeltech.banky.domain.CompteBancaireTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompteBancaireMapperTest {

    private CompteBancaireMapper compteBancaireMapper;

    @BeforeEach
    void setUp() {
        compteBancaireMapper = new CompteBancaireMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCompteBancaireSample1();
        var actual = compteBancaireMapper.toEntity(compteBancaireMapper.toDto(expected));
        assertCompteBancaireAllPropertiesEquals(expected, actual);
    }
}
