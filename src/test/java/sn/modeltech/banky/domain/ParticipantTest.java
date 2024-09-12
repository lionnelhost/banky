package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ParticipantTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ParticipantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participant.class);
        Participant participant1 = getParticipantSample1();
        Participant participant2 = new Participant();
        assertThat(participant1).isNotEqualTo(participant2);

        participant2.setIdParticipant(participant1.getIdParticipant());
        assertThat(participant1).isEqualTo(participant2);

        participant2 = getParticipantSample2();
        assertThat(participant1).isNotEqualTo(participant2);
    }
}
