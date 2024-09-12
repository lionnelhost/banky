package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.JourFerierTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class JourFerierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourFerier.class);
        JourFerier jourFerier1 = getJourFerierSample1();
        JourFerier jourFerier2 = new JourFerier();
        assertThat(jourFerier1).isNotEqualTo(jourFerier2);

        jourFerier2.setIdJourFerie(jourFerier1.getIdJourFerie());
        assertThat(jourFerier1).isEqualTo(jourFerier2);

        jourFerier2 = getJourFerierSample2();
        assertThat(jourFerier1).isNotEqualTo(jourFerier2);
    }
}
