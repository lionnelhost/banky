package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ParametrageGlobalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametrageGlobalDTO.class);
        ParametrageGlobalDTO parametrageGlobalDTO1 = new ParametrageGlobalDTO();
        parametrageGlobalDTO1.setIdParamGlobal("id1");
        ParametrageGlobalDTO parametrageGlobalDTO2 = new ParametrageGlobalDTO();
        assertThat(parametrageGlobalDTO1).isNotEqualTo(parametrageGlobalDTO2);
        parametrageGlobalDTO2.setIdParamGlobal(parametrageGlobalDTO1.getIdParamGlobal());
        assertThat(parametrageGlobalDTO1).isEqualTo(parametrageGlobalDTO2);
        parametrageGlobalDTO2.setIdParamGlobal("id2");
        assertThat(parametrageGlobalDTO1).isNotEqualTo(parametrageGlobalDTO2);
        parametrageGlobalDTO1.setIdParamGlobal(null);
        assertThat(parametrageGlobalDTO1).isNotEqualTo(parametrageGlobalDTO2);
    }
}
