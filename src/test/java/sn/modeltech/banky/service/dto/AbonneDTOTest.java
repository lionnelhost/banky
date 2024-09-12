package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class AbonneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbonneDTO.class);
        AbonneDTO abonneDTO1 = new AbonneDTO();
        abonneDTO1.setIdAbonne("id1");
        AbonneDTO abonneDTO2 = new AbonneDTO();
        assertThat(abonneDTO1).isNotEqualTo(abonneDTO2);
        abonneDTO2.setIdAbonne(abonneDTO1.getIdAbonne());
        assertThat(abonneDTO1).isEqualTo(abonneDTO2);
        abonneDTO2.setIdAbonne("id2");
        assertThat(abonneDTO1).isNotEqualTo(abonneDTO2);
        abonneDTO1.setIdAbonne(null);
        assertThat(abonneDTO1).isNotEqualTo(abonneDTO2);
    }
}
