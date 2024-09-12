package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ClientTestSamples.*;
import static sn.modeltech.banky.domain.CompteBancaireTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementCompteTestSamples.*;
import static sn.modeltech.banky.domain.ContratAbonnementTestSamples.*;
import static sn.modeltech.banky.domain.ContratTestSamples.*;
import static sn.modeltech.banky.domain.TypeContratTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ContratTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contrat.class);
        Contrat contrat1 = getContratSample1();
        Contrat contrat2 = new Contrat();
        assertThat(contrat1).isNotEqualTo(contrat2);

        contrat2.setIdContrat(contrat1.getIdContrat());
        assertThat(contrat1).isEqualTo(contrat2);

        contrat2 = getContratSample2();
        assertThat(contrat1).isNotEqualTo(contrat2);
    }

    @Test
    void typeContratTest() {
        Contrat contrat = getContratRandomSampleGenerator();
        TypeContrat typeContratBack = getTypeContratRandomSampleGenerator();

        contrat.setTypeContrat(typeContratBack);
        assertThat(contrat.getTypeContrat()).isEqualTo(typeContratBack);

        contrat.typeContrat(null);
        assertThat(contrat.getTypeContrat()).isNull();
    }

    @Test
    void clientTest() {
        Contrat contrat = getContratRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        contrat.setClient(clientBack);
        assertThat(contrat.getClient()).isEqualTo(clientBack);
        assertThat(clientBack.getContrat()).isEqualTo(contrat);

        contrat.client(null);
        assertThat(contrat.getClient()).isNull();
        assertThat(clientBack.getContrat()).isNull();
    }

    @Test
    void compteBancaireTest() {
        Contrat contrat = getContratRandomSampleGenerator();
        CompteBancaire compteBancaireBack = getCompteBancaireRandomSampleGenerator();

        contrat.addCompteBancaire(compteBancaireBack);
        assertThat(contrat.getCompteBancaires()).containsOnly(compteBancaireBack);
        assertThat(compteBancaireBack.getContrat()).isEqualTo(contrat);

        contrat.removeCompteBancaire(compteBancaireBack);
        assertThat(contrat.getCompteBancaires()).doesNotContain(compteBancaireBack);
        assertThat(compteBancaireBack.getContrat()).isNull();

        contrat.compteBancaires(new HashSet<>(Set.of(compteBancaireBack)));
        assertThat(contrat.getCompteBancaires()).containsOnly(compteBancaireBack);
        assertThat(compteBancaireBack.getContrat()).isEqualTo(contrat);

        contrat.setCompteBancaires(new HashSet<>());
        assertThat(contrat.getCompteBancaires()).doesNotContain(compteBancaireBack);
        assertThat(compteBancaireBack.getContrat()).isNull();
    }

    @Test
    void contratAbonnementTest() {
        Contrat contrat = getContratRandomSampleGenerator();
        ContratAbonnement contratAbonnementBack = getContratAbonnementRandomSampleGenerator();

        contrat.addContratAbonnement(contratAbonnementBack);
        assertThat(contrat.getContratAbonnements()).containsOnly(contratAbonnementBack);
        assertThat(contratAbonnementBack.getContrat()).isEqualTo(contrat);

        contrat.removeContratAbonnement(contratAbonnementBack);
        assertThat(contrat.getContratAbonnements()).doesNotContain(contratAbonnementBack);
        assertThat(contratAbonnementBack.getContrat()).isNull();

        contrat.contratAbonnements(new HashSet<>(Set.of(contratAbonnementBack)));
        assertThat(contrat.getContratAbonnements()).containsOnly(contratAbonnementBack);
        assertThat(contratAbonnementBack.getContrat()).isEqualTo(contrat);

        contrat.setContratAbonnements(new HashSet<>());
        assertThat(contrat.getContratAbonnements()).doesNotContain(contratAbonnementBack);
        assertThat(contratAbonnementBack.getContrat()).isNull();
    }

    @Test
    void contratAbonnementCompteTest() {
        Contrat contrat = getContratRandomSampleGenerator();
        ContratAbonnementCompte contratAbonnementCompteBack = getContratAbonnementCompteRandomSampleGenerator();

        contrat.addContratAbonnementCompte(contratAbonnementCompteBack);
        assertThat(contrat.getContratAbonnementComptes()).containsOnly(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getContrat()).isEqualTo(contrat);

        contrat.removeContratAbonnementCompte(contratAbonnementCompteBack);
        assertThat(contrat.getContratAbonnementComptes()).doesNotContain(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getContrat()).isNull();

        contrat.contratAbonnementComptes(new HashSet<>(Set.of(contratAbonnementCompteBack)));
        assertThat(contrat.getContratAbonnementComptes()).containsOnly(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getContrat()).isEqualTo(contrat);

        contrat.setContratAbonnementComptes(new HashSet<>());
        assertThat(contrat.getContratAbonnementComptes()).doesNotContain(contratAbonnementCompteBack);
        assertThat(contratAbonnementCompteBack.getContrat()).isNull();
    }
}
