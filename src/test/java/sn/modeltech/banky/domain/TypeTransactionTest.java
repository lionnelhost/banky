package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.DispositifSercuriteTestSamples.*;
import static sn.modeltech.banky.domain.TypeTransactionTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class TypeTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTransaction.class);
        TypeTransaction typeTransaction1 = getTypeTransactionSample1();
        TypeTransaction typeTransaction2 = new TypeTransaction();
        assertThat(typeTransaction1).isNotEqualTo(typeTransaction2);

        typeTransaction2.setIdTypeTransaction(typeTransaction1.getIdTypeTransaction());
        assertThat(typeTransaction1).isEqualTo(typeTransaction2);

        typeTransaction2 = getTypeTransactionSample2();
        assertThat(typeTransaction1).isNotEqualTo(typeTransaction2);
    }

    @Test
    void dispositifSercuriteTest() {
        TypeTransaction typeTransaction = getTypeTransactionRandomSampleGenerator();
        DispositifSercurite dispositifSercuriteBack = getDispositifSercuriteRandomSampleGenerator();

        typeTransaction.addDispositifSercurite(dispositifSercuriteBack);
        assertThat(typeTransaction.getDispositifSercurites()).containsOnly(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getTypeTransaction()).isEqualTo(typeTransaction);

        typeTransaction.removeDispositifSercurite(dispositifSercuriteBack);
        assertThat(typeTransaction.getDispositifSercurites()).doesNotContain(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getTypeTransaction()).isNull();

        typeTransaction.dispositifSercurites(new HashSet<>(Set.of(dispositifSercuriteBack)));
        assertThat(typeTransaction.getDispositifSercurites()).containsOnly(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getTypeTransaction()).isEqualTo(typeTransaction);

        typeTransaction.setDispositifSercurites(new HashSet<>());
        assertThat(typeTransaction.getDispositifSercurites()).doesNotContain(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getTypeTransaction()).isNull();
    }
}
