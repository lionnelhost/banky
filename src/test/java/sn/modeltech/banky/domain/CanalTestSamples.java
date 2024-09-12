package sn.modeltech.banky.domain;

import java.util.UUID;

public class CanalTestSamples {

    public static Canal getCanalSample1() {
        return new Canal().idCanal("idCanal1").libelle("libelle1");
    }

    public static Canal getCanalSample2() {
        return new Canal().idCanal("idCanal2").libelle("libelle2");
    }

    public static Canal getCanalRandomSampleGenerator() {
        return new Canal().idCanal(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
