package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class JourFerierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourFerierDTO.class);
        JourFerierDTO jourFerierDTO1 = new JourFerierDTO();
        jourFerierDTO1.setIdJourFerie("id1");
        JourFerierDTO jourFerierDTO2 = new JourFerierDTO();
        assertThat(jourFerierDTO1).isNotEqualTo(jourFerierDTO2);
        jourFerierDTO2.setIdJourFerie(jourFerierDTO1.getIdJourFerie());
        assertThat(jourFerierDTO1).isEqualTo(jourFerierDTO2);
        jourFerierDTO2.setIdJourFerie("id2");
        assertThat(jourFerierDTO1).isNotEqualTo(jourFerierDTO2);
        jourFerierDTO1.setIdJourFerie(null);
        assertThat(jourFerierDTO1).isNotEqualTo(jourFerierDTO2);
    }
}
