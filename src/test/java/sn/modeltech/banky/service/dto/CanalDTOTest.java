package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class CanalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CanalDTO.class);
        CanalDTO canalDTO1 = new CanalDTO();
        canalDTO1.setIdCanal("id1");
        CanalDTO canalDTO2 = new CanalDTO();
        assertThat(canalDTO1).isNotEqualTo(canalDTO2);
        canalDTO2.setIdCanal(canalDTO1.getIdCanal());
        assertThat(canalDTO1).isEqualTo(canalDTO2);
        canalDTO2.setIdCanal("id2");
        assertThat(canalDTO1).isNotEqualTo(canalDTO2);
        canalDTO1.setIdCanal(null);
        assertThat(canalDTO1).isNotEqualTo(canalDTO2);
    }
}
