package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.MessageErreurTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class MessageErreurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageErreur.class);
        MessageErreur messageErreur1 = getMessageErreurSample1();
        MessageErreur messageErreur2 = new MessageErreur();
        assertThat(messageErreur1).isNotEqualTo(messageErreur2);

        messageErreur2.setIdMessageErreur(messageErreur1.getIdMessageErreur());
        assertThat(messageErreur1).isEqualTo(messageErreur2);

        messageErreur2 = getMessageErreurSample2();
        assertThat(messageErreur1).isNotEqualTo(messageErreur2);
    }
}
