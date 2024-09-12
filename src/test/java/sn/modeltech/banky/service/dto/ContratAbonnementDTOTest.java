package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ContratAbonnementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratAbonnementDTO.class);
        ContratAbonnementDTO contratAbonnementDTO1 = new ContratAbonnementDTO();
        contratAbonnementDTO1.setId(1L);
        ContratAbonnementDTO contratAbonnementDTO2 = new ContratAbonnementDTO();
        assertThat(contratAbonnementDTO1).isNotEqualTo(contratAbonnementDTO2);
        contratAbonnementDTO2.setId(contratAbonnementDTO1.getId());
        assertThat(contratAbonnementDTO1).isEqualTo(contratAbonnementDTO2);
        contratAbonnementDTO2.setId(2L);
        assertThat(contratAbonnementDTO1).isNotEqualTo(contratAbonnementDTO2);
        contratAbonnementDTO1.setId(null);
        assertThat(contratAbonnementDTO1).isNotEqualTo(contratAbonnementDTO2);
    }
}
