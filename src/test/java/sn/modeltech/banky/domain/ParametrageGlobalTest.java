package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ParametrageGlobalTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ParametrageGlobalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametrageGlobal.class);
        ParametrageGlobal parametrageGlobal1 = getParametrageGlobalSample1();
        ParametrageGlobal parametrageGlobal2 = new ParametrageGlobal();
        assertThat(parametrageGlobal1).isNotEqualTo(parametrageGlobal2);

        parametrageGlobal2.setIdParamGlobal(parametrageGlobal1.getIdParamGlobal());
        assertThat(parametrageGlobal1).isEqualTo(parametrageGlobal2);

        parametrageGlobal2 = getParametrageGlobalSample2();
        assertThat(parametrageGlobal1).isNotEqualTo(parametrageGlobal2);
    }
}
