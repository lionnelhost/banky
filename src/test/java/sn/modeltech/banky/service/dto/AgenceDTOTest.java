package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class AgenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenceDTO.class);
        AgenceDTO agenceDTO1 = new AgenceDTO();
        agenceDTO1.setIdAgence("id1");
        AgenceDTO agenceDTO2 = new AgenceDTO();
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
        agenceDTO2.setIdAgence(agenceDTO1.getIdAgence());
        assertThat(agenceDTO1).isEqualTo(agenceDTO2);
        agenceDTO2.setIdAgence("id2");
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
        agenceDTO1.setIdAgence(null);
        assertThat(agenceDTO1).isNotEqualTo(agenceDTO2);
    }
}
