package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class VariableNotificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariableNotificationDTO.class);
        VariableNotificationDTO variableNotificationDTO1 = new VariableNotificationDTO();
        variableNotificationDTO1.setIdVarNotification("id1");
        VariableNotificationDTO variableNotificationDTO2 = new VariableNotificationDTO();
        assertThat(variableNotificationDTO1).isNotEqualTo(variableNotificationDTO2);
        variableNotificationDTO2.setIdVarNotification(variableNotificationDTO1.getIdVarNotification());
        assertThat(variableNotificationDTO1).isEqualTo(variableNotificationDTO2);
        variableNotificationDTO2.setIdVarNotification("id2");
        assertThat(variableNotificationDTO1).isNotEqualTo(variableNotificationDTO2);
        variableNotificationDTO1.setIdVarNotification(null);
        assertThat(variableNotificationDTO1).isNotEqualTo(variableNotificationDTO2);
    }
}
