package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class CompteBancaireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteBancaireDTO.class);
        CompteBancaireDTO compteBancaireDTO1 = new CompteBancaireDTO();
        compteBancaireDTO1.setIdCompteBancaire("id1");
        CompteBancaireDTO compteBancaireDTO2 = new CompteBancaireDTO();
        assertThat(compteBancaireDTO1).isNotEqualTo(compteBancaireDTO2);
        compteBancaireDTO2.setIdCompteBancaire(compteBancaireDTO1.getIdCompteBancaire());
        assertThat(compteBancaireDTO1).isEqualTo(compteBancaireDTO2);
        compteBancaireDTO2.setIdCompteBancaire("id2");
        assertThat(compteBancaireDTO1).isNotEqualTo(compteBancaireDTO2);
        compteBancaireDTO1.setIdCompteBancaire(null);
        assertThat(compteBancaireDTO1).isNotEqualTo(compteBancaireDTO2);
    }
}
