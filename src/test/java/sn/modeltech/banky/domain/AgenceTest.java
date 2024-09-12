package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.AgenceTestSamples.*;
import static sn.modeltech.banky.domain.BanqueTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class AgenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agence.class);
        Agence agence1 = getAgenceSample1();
        Agence agence2 = new Agence();
        assertThat(agence1).isNotEqualTo(agence2);

        agence2.setIdAgence(agence1.getIdAgence());
        assertThat(agence1).isEqualTo(agence2);

        agence2 = getAgenceSample2();
        assertThat(agence1).isNotEqualTo(agence2);
    }

    @Test
    void banqueTest() {
        Agence agence = getAgenceRandomSampleGenerator();
        Banque banqueBack = getBanqueRandomSampleGenerator();

        agence.setBanque(banqueBack);
        assertThat(agence.getBanque()).isEqualTo(banqueBack);

        agence.banque(null);
        assertThat(agence.getBanque()).isNull();
    }
}
