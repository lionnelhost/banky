package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.CompteBancaireTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementCompteTestSamples.*;
import static sn.modeltech.banky.domain.ContratTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class CompteBancaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteBancaire.class);
        CompteBancaire compteBancaire1 = getCompteBancaireSample1();
        CompteBancaire compteBancaire2 = new CompteBancaire();
        assertThat(compteBancaire1).isNotEqualTo(compteBancaire2);

        compteBancaire2.setIdCompteBancaire(compteBancaire1.getIdCompteBancaire());
        assertThat(compteBancaire1).isEqualTo(compteBancaire2);

        compteBancaire2 = getCompteBancaireSample2();
        assertThat(compteBancaire1).isNotEqualTo(compteBancaire2);
    }

    @Test
    void contratTest() {
        CompteBancaire compteBancaire = getCompteBancaireRandomSampleGenerator();
        Contrat contratBack = getContratRandomSampleGenerator();

        compteBancaire.setContrat(contratBack);
        assertThat(compteBancaire.getContrat()).isEqualTo(contratBack);

        compteBancaire.contrat(null);
        assertThat(compteBancaire.getContrat()).isNull();
    }

    @Test
    void contratAbonnementCompteTest() {
        CompteBancaire compteBancaire = getCompteBancaireRandomSampleGenerator();
        ContratAbonnementCompte contratAbonnementCompteBack = getContratAbonnementCompteRandomSampleGenerator();

        compteBancaire.addContratAbonnementCompte(contratAbonnementCompteBack);
        assertThat(compteBancaire.getContratAbonnementComptes()).containsOnly(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getCompteBancaire()).isEqualTo(compteBancaire);

        compteBancaire.removeContratAbonnementCompte(contratAbonnementCompteBack);
        assertThat(compteBancaire.getContratAbonnementComptes()).doesNotContain(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getCompteBancaire()).isNull();

        compteBancaire.contratAbonnementComptes(new HashSet<>(Set.of(contratAbonnementCompteBack)));
        assertThat(compteBancaire.getContratAbonnementComptes()).containsOnly(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getCompteBancaire()).isEqualTo(compteBancaire);

        compteBancaire.setContratAbonnementComptes(new HashSet<>());
        assertThat(compteBancaire.getContratAbonnementComptes()).doesNotContain(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getCompteBancaire()).isNull();
    }
}
