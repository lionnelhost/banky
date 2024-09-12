package sn.modeltech.banky.domain;

import java.util.UUID;

public class JourFerierTestSamples {

    public static JourFerier getJourFerierSample1() {
        return new JourFerier().idJourFerie("idJourFerie1").libelle("libelle1");
    }

    public static JourFerier getJourFerierSample2() {
        return new JourFerier().idJourFerie("idJourFerie2").libelle("libelle2");
    }

    public static JourFerier getJourFerierRandomSampleGenerator() {
        return new JourFerier().idJourFerie(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
