package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.CanalTestSamples.*;
import static sn.modeltech.banky.domain.DispositifSercuriteTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class CanalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Canal.class);
        Canal canal1 = getCanalSample1();
        Canal canal2 = new Canal();
        assertThat(canal1).isNotEqualTo(canal2);

        canal2.setIdCanal(canal1.getIdCanal());
        assertThat(canal1).isEqualTo(canal2);

        canal2 = getCanalSample2();
        assertThat(canal1).isNotEqualTo(canal2);
    }

    @Test
    void dispositifSercuriteTest() {
        Canal canal = getCanalRandomSampleGenerator();
        DispositifSercurite dispositifSercuriteBack = getDispositifSercuriteRandomSampleGenerator();

        canal.addDispositifSercurite(dispositifSercuriteBack);
        assertThat(canal.getDispositifSercurites()).containsOnly(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getCanal()).isEqualTo(canal);

        canal.removeDispositifSercurite(dispositifSercuriteBack);
        assertThat(canal.getDispositifSercurites()).doesNotContain(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getCanal()).isNull();

        canal.dispositifSercurites(new HashSet<>(Set.of(dispositifSercuriteBack)));
        assertThat(canal.getDispositifSercurites()).containsOnly(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getCanal()).isEqualTo(canal);

        canal.setDispositifSercurites(new HashSet<>());
        assertThat(canal.getDispositifSercurites()).doesNotContain(dispositifSercuriteBack);
        assertThat(dispositifSercuriteBack.getCanal()).isNull();
    }
}
