package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.AbonneTestSamples.*;
import static sn.modeltech.banky.domain.CompteBancaireTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementCompteTestSamples.*;
import static sn.modeltech.banky.domain.ContratTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ContratAbonnementCompteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratAbonnementCompte.class);
        ContratAbonnementCompte contratAbonnementCompte1 = getContratAbonnementCompteSample1();
        ContratAbonnementCompte contratAbonnementCompte2 = new ContratAbonnementCompte();
        assertThat(contratAbonnementCompte1).isNotEqualTo(contratAbonnementCompte2);

        contratAbonnementCompte2.setId(contratAbonnementCompte1.getId());
        assertThat(contratAbonnementCompte1).isEqualTo(contratAbonnementCompte2);

        contratAbonnementCompte2 = getContratAbonnementCompteSample2();
        assertThat(contratAbonnementCompte1).isNotEqualTo(contratAbonnementCompte2);
    }

    @Test
    void contratTest() {
        ContratAbonnementCompte contratAbonnementCompte = getContratAbonnementCompteRandomSampleGenerator();
        Contrat contratBack = getContratRandomSampleGenerator();

        contratAbonnementCompte.setContrat(contratBack);
        assertThat(contratAbonnementCompte.getContrat()).isEqualTo(contratBack);

        contratAbonnementCompte.contrat(null);
        assertThat(contratAbonnementCompte.getContrat()).isNull();
    }

    @Test
    void abonneTest() {
        ContratAbonnementCompte contratAbonnementCompte = getContratAbonnementCompteRandomSampleGenerator();
        Abonne abonneBack = getAbonneRandomSampleGenerator();

        contratAbonnementCompte.setAbonne(abonneBack);
        assertThat(contratAbonnementCompte.getAbonne()).isEqualTo(abonneBack);

        contratAbonnementCompte.abonne(null);
        assertThat(contratAbonnementCompte.getAbonne()).isNull();
    }

    @Test
    void compteBancaireTest() {
        ContratAbonnementCompte contratAbonnementCompte = getContratAbonnementCompteRandomSampleGenerator();
        CompteBancaire compteBancaireBack = getCompteBancaireRandomSampleGenerator();

        contratAbonnementCompte.setCompteBancaire(compteBancaireBack);
        assertThat(contratAbonnementCompte.getCompteBancaire()).isEqualTo(compteBancaireBack);

        contratAbonnementCompte.compteBancaire(null);
        assertThat(contratAbonnementCompte.getCompteBancaire()).isNull();
    }
}
