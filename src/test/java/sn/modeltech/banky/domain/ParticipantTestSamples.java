package sn.modeltech.banky.domain;

import java.util.UUID;

public class ParticipantTestSamples {

    public static Participant getParticipantSample1() {
        return new Participant()
            .idParticipant("idParticipant1")
            .codeParticipant("codeParticipant1")
            .codeBanque("codeBanque1")
            .nomBanque("nomBanque1")
            .libelle("libelle1")
            .pays("pays1");
    }

    public static Participant getParticipantSample2() {
        return new Participant()
            .idParticipant("idParticipant2")
            .codeParticipant("codeParticipant2")
            .codeBanque("codeBanque2")
            .nomBanque("nomBanque2")
            .libelle("libelle2")
            .pays("pays2");
    }

    public static Participant getParticipantRandomSampleGenerator() {
        return new Participant()
            .idParticipant(UUID.randomUUID().toString())
            .codeParticipant(UUID.randomUUID().toString())
            .codeBanque(UUID.randomUUID().toString())
            .nomBanque(UUID.randomUUID().toString())
            .libelle(UUID.randomUUID().toString())
            .pays(UUID.randomUUID().toString());
    }
}
