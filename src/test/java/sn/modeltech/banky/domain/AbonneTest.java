package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.AbonneTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementCompteTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class AbonneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Abonne.class);
        Abonne abonne1 = getAbonneSample1();
        Abonne abonne2 = new Abonne();
        assertThat(abonne1).isNotEqualTo(abonne2);

        abonne2.setIdAbonne(abonne1.getIdAbonne());
        assertThat(abonne1).isEqualTo(abonne2);

        abonne2 = getAbonneSample2();
        assertThat(abonne1).isNotEqualTo(abonne2);
    }

    @Test
    void contratAbonnementTest() {
        Abonne abonne = getAbonneRandomSampleGenerator();
        ContratAbonnement contratAbonnementBack = getContratAbonnementRandomSampleGenerator();

        abonne.addContratAbonnement(contratAbonnementBack);
        assertThat(abonne.getContratAbonnements()).containsOnly(contratAbonnementBack);
        assertThat(contratAbonnementBack.getAbonne()).isEqualTo(abonne);

        abonne.removeContratAbonnement(contratAbonnementBack);
        assertThat(abonne.getContratAbonnements()).doesNotContain(contratAbonnementBack);
        assertThat(contratAbonnementBack.getAbonne()).isNull();

        abonne.contratAbonnements(new HashSet<>(Set.of(contratAbonnementBack)));
        assertThat(abonne.getContratAbonnements()).containsOnly(contratAbonnementBack);
        assertThat(contratAbonnementBack.getAbonne()).isEqualTo(abonne);

        abonne.setContratAbonnements(new HashSet<>());
        assertThat(abonne.getContratAbonnements()).doesNotContain(contratAbonnementBack);
        assertThat(contratAbonnementBack.getAbonne()).isNull();
    }

    @Test
    void contratAbonnementCompteTest() {
        Abonne abonne = getAbonneRandomSampleGenerator();
        ContratAbonnementCompte contratAbonnementCompteBack = getContratAbonnementCompteRandomSampleGenerator();

        abonne.addContratAbonnementCompte(contratAbonnementCompteBack);
        assertThat(abonne.getContratAbonnementComptes()).containsOnly(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getAbonne()).isEqualTo(abonne);

        abonne.removeContratAbonnementCompte(contratAbonnementCompteBack);
        assertThat(abonne.getContratAbonnementComptes()).doesNotContain(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getAbonne()).isNull();

        abonne.contratAbonnementComptes(new HashSet<>(Set.of(contratAbonnementCompteBack)));
        assertThat(abonne.getContratAbonnementComptes()).containsOnly(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getAbonne()).isEqualTo(abonne);

        abonne.setContratAbonnementComptes(new HashSet<>());
        assertThat(abonne.getContratAbonnementComptes()).doesNotContain(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getAbonne()).isNull();
    }
}
