package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.AbonneTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementTestSamples.*;
import static sn.modeltech.banky.domain.ContratTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ContratAbonnementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratAbonnement.class);
        ContratAbonnement contratAbonnement1 = getContratAbonnementSample1();
        ContratAbonnement contratAbonnement2 = new ContratAbonnement();
        assertThat(contratAbonnement1).isNotEqualTo(contratAbonnement2);

        contratAbonnement2.setId(contratAbonnement1.getId());
        assertThat(contratAbonnement1).isEqualTo(contratAbonnement2);

        contratAbonnement2 = getContratAbonnementSample2();
        assertThat(contratAbonnement1).isNotEqualTo(contratAbonnement2);
    }

    @Test
    void contratTest() {
        ContratAbonnement contratAbonnement = getContratAbonnementRandomSampleGenerator();
        Contrat contratBack = getContratRandomSampleGenerator();

        contratAbonnement.setContrat(contratBack);
        assertThat(contratAbonnement.getContrat()).isEqualTo(contratBack);

        contratAbonnement.contrat(null);
        assertThat(contratAbonnement.getContrat()).isNull();
    }

    @Test
    void abonneTest() {
        ContratAbonnement contratAbonnement = getContratAbonnementRandomSampleGenerator();
        Abonne abonneBack = getAbonneRandomSampleGenerator();

        contratAbonnement.setAbonne(abonneBack);
        assertThat(contratAbonnement.getAbonne()).isEqualTo(abonneBack);

        contratAbonnement.abonne(null);
        assertThat(contratAbonnement.getAbonne()).isNull();
    }
}
