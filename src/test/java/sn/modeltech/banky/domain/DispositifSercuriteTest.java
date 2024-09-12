package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.CanalTestSamples.*;
import static sn.modeltech.banky.domain.DispositifSercuriteTestSamples.*;
import static sn.modeltech.banky.domain.DispositifSignatureTestSamples.*;
import static sn.modeltech.banky.domain.TypeTransactionTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class DispositifSercuriteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositifSercurite.class);
        DispositifSercurite dispositifSercurite1 = getDispositifSercuriteSample1();
        DispositifSercurite dispositifSercurite2 = new DispositifSercurite();
        assertThat(dispositifSercurite1).isNotEqualTo(dispositifSercurite2);

        dispositifSercurite2.setId(dispositifSercurite1.getId());
        assertThat(dispositifSercurite1).isEqualTo(dispositifSercurite2);

        dispositifSercurite2 = getDispositifSercuriteSample2();
        assertThat(dispositifSercurite1).isNotEqualTo(dispositifSercurite2);
    }

    @Test
    void canalTest() {
        DispositifSercurite dispositifSercurite = getDispositifSercuriteRandomSampleGenerator();
        Canal canalBack = getCanalRandomSampleGenerator();

        dispositifSercurite.setCanal(canalBack);
        assertThat(dispositifSercurite.getCanal()).isEqualTo(canalBack);

        dispositifSercurite.canal(null);
        assertThat(dispositifSercurite.getCanal()).isNull();
    }

    @Test
    void typeTransactionTest() {
        DispositifSercurite dispositifSercurite = getDispositifSercuriteRandomSampleGenerator();
        TypeTransaction typeTransactionBack = getTypeTransactionRandomSampleGenerator();

        dispositifSercurite.setTypeTransaction(typeTransactionBack);
        assertThat(dispositifSercurite.getTypeTransaction()).isEqualTo(typeTransactionBack);

        dispositifSercurite.typeTransaction(null);
        assertThat(dispositifSercurite.getTypeTransaction()).isNull();
    }

    @Test
    void dispositifSignatureTest() {
        DispositifSercurite dispositifSercurite = getDispositifSercuriteRandomSampleGenerator();
        DispositifSignature dispositifSignatureBack = getDispositifSignatureRandomSampleGenerator();

        dispositifSercurite.setDispositifSignature(dispositifSignatureBack);
        assertThat(dispositifSercurite.getDispositifSignature()).isEqualTo(dispositifSignatureBack);

        dispositifSercurite.dispositifSignature(null);
        assertThat(dispositifSercurite.getDispositifSignature()).isNull();
    }
}
