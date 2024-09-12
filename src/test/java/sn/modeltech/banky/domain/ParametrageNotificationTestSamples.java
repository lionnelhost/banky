package sn.modeltech.banky.domain;

import java.util.UUID;

public class ParametrageNotificationTestSamples {

    public static ParametrageNotification getParametrageNotificationSample1() {
        return new ParametrageNotification()
            .idParamNotif("idParamNotif1")
            .objetNotif("objetNotif1")
            .typeNotif("typeNotif1")
            .contenu("contenu1");
    }

    public static ParametrageNotification getParametrageNotificationSample2() {
        return new ParametrageNotification()
            .idParamNotif("idParamNotif2")
            .objetNotif("objetNotif2")
            .typeNotif("typeNotif2")
            .contenu("contenu2");
    }

    public static ParametrageNotification getParametrageNotificationRandomSampleGenerator() {
        return new ParametrageNotification()
            .idParamNotif(UUID.randomUUID().toString())
            .objetNotif(UUID.randomUUID().toString())
            .typeNotif(UUID.randomUUID().toString())
            .contenu(UUID.randomUUID().toString());
    }
}
