package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.VariableNotificationTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class VariableNotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariableNotification.class);
        VariableNotification variableNotification1 = getVariableNotificationSample1();
        VariableNotification variableNotification2 = new VariableNotification();
        assertThat(variableNotification1).isNotEqualTo(variableNotification2);

        variableNotification2.setIdVarNotification(variableNotification1.getIdVarNotification());
        assertThat(variableNotification1).isEqualTo(variableNotification2);

        variableNotification2 = getVariableNotificationSample2();
        assertThat(variableNotification1).isNotEqualTo(variableNotification2);
    }
}
