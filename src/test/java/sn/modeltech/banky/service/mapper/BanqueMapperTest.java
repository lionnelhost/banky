package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.BanqueAsserts.*;
import static sn.modeltech.banky.domain.BanqueTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BanqueMapperTest {

    private BanqueMapper banqueMapper;

    @BeforeEach
    void setUp() {
        banqueMapper = new BanqueMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBanqueSample1();
        var actual = banqueMapper.toEntity(banqueMapper.toDto(expected));
        assertBanqueAllPropertiesEquals(expected, actual);
    }
}
