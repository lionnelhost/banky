package sn.modeltech.banky.domain;

import java.util.UUID;

public class AgenceTestSamples {

    public static Agence getAgenceSample1() {
        return new Agence().idAgence("idAgence1").codeAgence("codeAgence1").nomAgence("nomAgence1");
    }

    public static Agence getAgenceSample2() {
        return new Agence().idAgence("idAgence2").codeAgence("codeAgence2").nomAgence("nomAgence2");
    }

    public static Agence getAgenceRandomSampleGenerator() {
        return new Agence()
            .idAgence(UUID.randomUUID().toString())
            .codeAgence(UUID.randomUUID().toString())
            .nomAgence(UUID.randomUUID().toString());
    }
}
