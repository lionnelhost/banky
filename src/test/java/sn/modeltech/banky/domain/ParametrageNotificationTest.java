package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ParametrageNotificationTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ParametrageNotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametrageNotification.class);
        ParametrageNotification parametrageNotification1 = getParametrageNotificationSample1();
        ParametrageNotification parametrageNotification2 = new ParametrageNotification();
        assertThat(parametrageNotification1).isNotEqualTo(parametrageNotification2);

        parametrageNotification2.setIdParamNotif(parametrageNotification1.getIdParamNotif());
        assertThat(parametrageNotification1).isEqualTo(parametrageNotification2);

        parametrageNotification2 = getParametrageNotificationSample2();
        assertThat(parametrageNotification1).isNotEqualTo(parametrageNotification2);
    }
}
