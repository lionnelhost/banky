package sn.modeltech.banky.domain;

import java.util.UUID;

public class TypeContratTestSamples {

    public static TypeContrat getTypeContratSample1() {
        return new TypeContrat().idTypeContrat("idTypeContrat1").libelle("libelle1");
    }

    public static TypeContrat getTypeContratSample2() {
        return new TypeContrat().idTypeContrat("idTypeContrat2").libelle("libelle2");
    }

    public static TypeContrat getTypeContratRandomSampleGenerator() {
        return new TypeContrat().idTypeContrat(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
