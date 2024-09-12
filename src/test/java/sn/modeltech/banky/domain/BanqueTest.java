package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.AgenceTestSamples.*;
import static sn.modeltech.banky.domain.BanqueTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class BanqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banque.class);
        Banque banque1 = getBanqueSample1();
        Banque banque2 = new Banque();
        assertThat(banque1).isNotEqualTo(banque2);

        banque2.setIdBanque(banque1.getIdBanque());
        assertThat(banque1).isEqualTo(banque2);

        banque2 = getBanqueSample2();
        assertThat(banque1).isNotEqualTo(banque2);
    }

    @Test
    void agenceTest() {
        Banque banque = getBanqueRandomSampleGenerator();
        Agence agenceBack = getAgenceRandomSampleGenerator();

        banque.addAgence(agenceBack);
        assertThat(banque.getAgences()).containsOnly(agenceBack);
        assertThat(agenceBack.getBanque()).isEqualTo(banque);

        banque.removeAgence(agenceBack);
        assertThat(banque.getAgences()).doesNotContain(agenceBack);
        assertThat(agenceBack.getBanque()).isNull();

        banque.agences(new HashSet<>(Set.of(agenceBack)));
        assertThat(banque.getAgences()).containsOnly(agenceBack);
        assertThat(agenceBack.getBanque()).isEqualTo(banque);

        banque.setAgences(new HashSet<>());
        assertThat(banque.getAgences()).doesNotContain(agenceBack);
        assertThat(agenceBack.getBanque()).isNull();
    }
}
