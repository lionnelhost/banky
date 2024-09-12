package sn.modeltech.banky.service.mapper;

import static sn.modeltech.banky.domain.MessageErreurAsserts.*;
import static sn.modeltech.banky.domain.MessageErreurTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageErreurMapperTest {

    private MessageErreurMapper messageErreurMapper;

    @BeforeEach
    void setUp() {
        messageErreurMapper = new MessageErreurMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMessageErreurSample1();
        var actual = messageErreurMapper.toEntity(messageErreurMapper.toDto(expected));
        assertMessageErreurAllPropertiesEquals(expected, actual);
    }
}
