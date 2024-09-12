package sn.modeltech.banky.domain;

import java.util.UUID;

public class TypeClientTestSamples {

    public static TypeClient getTypeClientSample1() {
        return new TypeClient().idTypeClient("idTypeClient1").libelle("libelle1");
    }

    public static TypeClient getTypeClientSample2() {
        return new TypeClient().idTypeClient("idTypeClient2").libelle("libelle2");
    }

    public static TypeClient getTypeClientRandomSampleGenerator() {
        return new TypeClient().idTypeClient(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
