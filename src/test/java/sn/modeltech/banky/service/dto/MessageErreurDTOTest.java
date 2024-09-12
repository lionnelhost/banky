package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class MessageErreurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageErreurDTO.class);
        MessageErreurDTO messageErreurDTO1 = new MessageErreurDTO();
        messageErreurDTO1.setIdMessageErreur("id1");
        MessageErreurDTO messageErreurDTO2 = new MessageErreurDTO();
        assertThat(messageErreurDTO1).isNotEqualTo(messageErreurDTO2);
        messageErreurDTO2.setIdMessageErreur(messageErreurDTO1.getIdMessageErreur());
        assertThat(messageErreurDTO1).isEqualTo(messageErreurDTO2);
        messageErreurDTO2.setIdMessageErreur("id2");
        assertThat(messageErreurDTO1).isNotEqualTo(messageErreurDTO2);
        messageErreurDTO1.setIdMessageErreur(null);
        assertThat(messageErreurDTO1).isNotEqualTo(messageErreurDTO2);
    }
}
