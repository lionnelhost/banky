package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class BanqueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanqueDTO.class);
        BanqueDTO banqueDTO1 = new BanqueDTO();
        banqueDTO1.setIdBanque("id1");
        BanqueDTO banqueDTO2 = new BanqueDTO();
        assertThat(banqueDTO1).isNotEqualTo(banqueDTO2);
        banqueDTO2.setIdBanque(banqueDTO1.getIdBanque());
        assertThat(banqueDTO1).isEqualTo(banqueDTO2);
        banqueDTO2.setIdBanque("id2");
        assertThat(banqueDTO1).isNotEqualTo(banqueDTO2);
        banqueDTO1.setIdBanque(null);
        assertThat(banqueDTO1).isNotEqualTo(banqueDTO2);
    }
}
