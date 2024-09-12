package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ContratDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratDTO.class);
        ContratDTO contratDTO1 = new ContratDTO();
        contratDTO1.setIdContrat("id1");
        ContratDTO contratDTO2 = new ContratDTO();
        assertThat(contratDTO1).isNotEqualTo(contratDTO2);
        contratDTO2.setIdContrat(contratDTO1.getIdContrat());
        assertThat(contratDTO1).isEqualTo(contratDTO2);
        contratDTO2.setIdContrat("id2");
        assertThat(contratDTO1).isNotEqualTo(contratDTO2);
        contratDTO1.setIdContrat(null);
        assertThat(contratDTO1).isNotEqualTo(contratDTO2);
    }
}
