package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ParametrageNotificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametrageNotificationDTO.class);
        ParametrageNotificationDTO parametrageNotificationDTO1 = new ParametrageNotificationDTO();
        parametrageNotificationDTO1.setIdParamNotif("id1");
        ParametrageNotificationDTO parametrageNotificationDTO2 = new ParametrageNotificationDTO();
        assertThat(parametrageNotificationDTO1).isNotEqualTo(parametrageNotificationDTO2);
        parametrageNotificationDTO2.setIdParamNotif(parametrageNotificationDTO1.getIdParamNotif());
        assertThat(parametrageNotificationDTO1).isEqualTo(parametrageNotificationDTO2);
        parametrageNotificationDTO2.setIdParamNotif("id2");
        assertThat(parametrageNotificationDTO1).isNotEqualTo(parametrageNotificationDTO2);
        parametrageNotificationDTO1.setIdParamNotif(null);
        assertThat(parametrageNotificationDTO1).isNotEqualTo(parametrageNotificationDTO2);
    }
}
