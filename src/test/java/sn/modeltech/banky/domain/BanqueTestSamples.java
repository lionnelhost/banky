package sn.modeltech.banky.domain;

import java.util.UUID;

public class BanqueTestSamples {

    public static Banque getBanqueSample1() {
        return new Banque()
            .idBanque("idBanque1")
            .code("code1")
            .logo("logo1")
            .codeSwift("codeSwift1")
            .codeIban("codeIban1")
            .codePays("codePays1")
            .fuseauHoraire("fuseauHoraire1")
            .cutOffTime("cutOffTime1")
            .codeParticipant("codeParticipant1")
            .libelleParticipant("libelleParticipant1");
    }

    public static Banque getBanqueSample2() {
        return new Banque()
            .idBanque("idBanque2")
            .code("code2")
            .logo("logo2")
            .codeSwift("codeSwift2")
            .codeIban("codeIban2")
            .codePays("codePays2")
            .fuseauHoraire("fuseauHoraire2")
            .cutOffTime("cutOffTime2")
            .codeParticipant("codeParticipant2")
            .libelleParticipant("libelleParticipant2");
    }

    public static Banque getBanqueRandomSampleGenerator() {
        return new Banque()
            .idBanque(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .logo(UUID.randomUUID().toString())
            .codeSwift(UUID.randomUUID().toString())
            .codeIban(UUID.randomUUID().toString())
            .codePays(UUID.randomUUID().toString())
            .fuseauHoraire(UUID.randomUUID().toString())
            .cutOffTime(UUID.randomUUID().toString())
            .codeParticipant(UUID.randomUUID().toString())
            .libelleParticipant(UUID.randomUUID().toString());
    }
}
