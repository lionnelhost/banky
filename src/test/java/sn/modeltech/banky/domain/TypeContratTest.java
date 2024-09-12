package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ContratTestSamples.*;
import static sn.modeltech.banky.domain.TypeContratTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class TypeContratTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeContrat.class);
        TypeContrat typeContrat1 = getTypeContratSample1();
        TypeContrat typeContrat2 = new TypeContrat();
        assertThat(typeContrat1).isNotEqualTo(typeContrat2);

        typeContrat2.setIdTypeContrat(typeContrat1.getIdTypeContrat());
        assertThat(typeContrat1).isEqualTo(typeContrat2);

        typeContrat2 = getTypeContratSample2();
        assertThat(typeContrat1).isNotEqualTo(typeContrat2);
    }

    @Test
    void contratTest() {
        TypeContrat typeContrat = getTypeContratRandomSampleGenerator();
        Contrat contratBack = getContratRandomSampleGenerator();

        typeContrat.addContrat(contratBack);
        assertThat(typeContrat.getContrats()).containsOnly(contratBack);
        assertThat(contratBack.getTypeContrat()).isEqualTo(typeContrat);

        typeContrat.removeContrat(contratBack);
        assertThat(typeContrat.getContrats()).doesNotContain(contratBack);
        assertThat(contratBack.getTypeContrat()).isNull();

        typeContrat.contrats(new HashSet<>(Set.of(contratBack)));
        assertThat(typeContrat.getContrats()).containsOnly(contratBack);
        assertThat(contratBack.getTypeContrat()).isEqualTo(typeContrat);

        typeContrat.setContrats(new HashSet<>());
        assertThat(typeContrat.getContrats()).doesNotContain(contratBack);
        assertThat(contratBack.getTypeContrat()).isNull();
    }
}
