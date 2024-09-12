package sn.modeltech.banky.domain;

import java.util.UUID;

public class VariableNotificationTestSamples {

    public static VariableNotification getVariableNotificationSample1() {
        return new VariableNotification().idVarNotification("idVarNotification1").codeVariable("codeVariable1").description("description1");
    }

    public static VariableNotification getVariableNotificationSample2() {
        return new VariableNotification().idVarNotification("idVarNotification2").codeVariable("codeVariable2").description("description2");
    }

    public static VariableNotification getVariableNotificationRandomSampleGenerator() {
        return new VariableNotification()
            .idVarNotification(UUID.randomUUID().toString())
            .codeVariable(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
