package sn.modeltech.banky.domain;

import java.util.UUID;

public class ContratTestSamples {

    public static Contrat getContratSample1() {
        return new Contrat().idContrat("idContrat1");
    }

    public static Contrat getContratSample2() {
        return new Contrat().idContrat("idContrat2");
    }

    public static Contrat getContratRandomSampleGenerator() {
        return new Contrat().idContrat(UUID.randomUUID().toString());
    }
}
