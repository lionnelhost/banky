package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.DispositifSercuriteTestSamples.*;
import static sn.modeltech.banky.domain.DispositifSignatureTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class DispositifSignatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositifSignature.class);
        DispositifSignature dispositifSignature1 = getDispositifSignatureSample1();
        DispositifSignature dispositifSignature2 = new DispositifSignature();
        assertThat(dispositifSignature1).isNotEqualTo(dispositifSignature2);

        dispositifSignature2.setIdDispositif(dispositifSignature1.getIdDispositif());
        assertThat(dispositifSignature1).isEqualTo(dispositifSignature2);

        dispositifSignature2 = getDispositifSignatureSample2();
        assertThat(dispositifSignature1).isNotEqualTo(dispositifSignature2);
    }

    @Test
    void dispositifSercuriteTest() {
        DispositifSignature dispositifSignature = getDispositifSignatureRandomSampleGenerator();
        DispositifSercurite dispositifSercuriteBack = getDispositifSercuriteRandomSampleGenerator();

        dispositifSignature.addDispositifSercurite(dispositifSercuriteBack);
        assertThat(dispositifSignature.getDispositifSercurites()).containsOnly(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getDispositifSignature()).isEqualTo(dispositifSignature);

        dispositifSignature.removeDispositifSercurite(dispositifSercuriteBack);
        assertThat(dispositifSignature.getDispositifSercurites()).doesNotContain(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getDispositifSignature()).isNull();

        dispositifSignature.dispositifSercurites(new HashSet<>(Set.of(dispositifSercuriteBack)));
        assertThat(dispositifSignature.getDispositifSercurites()).containsOnly(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getDispositifSignature()).isEqualTo(dispositifSignature);

        dispositifSignature.setDispositifSercurites(new HashSet<>());
        assertThat(dispositifSignature.getDispositifSercurites()).doesNotContain(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getDispositifSignature()).isNull();
    }
}
