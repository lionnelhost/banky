package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ContratAbonnementCompteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratAbonnementCompteDTO.class);
        ContratAbonnementCompteDTO contratAbonnementCompteDTO1 = new ContratAbonnementCompteDTO();
        contratAbonnementCompteDTO1.setId(1L);
        ContratAbonnementCompteDTO contratAbonnementCompteDTO2 = new ContratAbonnementCompteDTO();
        assertThat(contratAbonnementCompteDTO1).isNotEqualTo(contratAbonnementCompteDTO2);
        contratAbonnementCompteDTO2.setId(contratAbonnementCompteDTO1.getId());
        assertThat(contratAbonnementCompteDTO1).isEqualTo(contratAbonnementCompteDTO2);
        contratAbonnementCompteDTO2.setId(2L);
        assertThat(contratAbonnementCompteDTO1).isNotEqualTo(contratAbonnementCompteDTO2);
        contratAbonnementCompteDTO1.setId(null);
        assertThat(contratAbonnementCompteDTO1).isNotEqualTo(contratAbonnementCompteDTO2);
    }
}
