package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ParticipantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipantDTO.class);
        ParticipantDTO participantDTO1 = new ParticipantDTO();
        participantDTO1.setIdParticipant("id1");
        ParticipantDTO participantDTO2 = new ParticipantDTO();
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
        participantDTO2.setIdParticipant(participantDTO1.getIdParticipant());
        assertThat(participantDTO1).isEqualTo(participantDTO2);
        participantDTO2.setIdParticipant("id2");
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
        participantDTO1.setIdParticipant(null);
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
    }
}
