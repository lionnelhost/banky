package sn.modeltech.banky.domain;

import java.util.UUID;

public class MessageErreurTestSamples {

    public static MessageErreur getMessageErreurSample1() {
        return new MessageErreur().idMessageErreur("idMessageErreur1").codeErreur("codeErreur1").description("description1");
    }

    public static MessageErreur getMessageErreurSample2() {
        return new MessageErreur().idMessageErreur("idMessageErreur2").codeErreur("codeErreur2").description("description2");
    }

    public static MessageErreur getMessageErreurRandomSampleGenerator() {
        return new MessageErreur()
            .idMessageErreur(UUID.randomUUID().toString())
            .codeErreur(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
