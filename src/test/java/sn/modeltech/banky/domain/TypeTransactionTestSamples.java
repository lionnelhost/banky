package sn.modeltech.banky.domain;

import java.util.UUID;

public class TypeTransactionTestSamples {

    public static TypeTransaction getTypeTransactionSample1() {
        return new TypeTransaction().idTypeTransaction("idTypeTransaction1").libelle("libelle1");
    }

    public static TypeTransaction getTypeTransactionSample2() {
        return new TypeTransaction().idTypeTransaction("idTypeTransaction2").libelle("libelle2");
    }

    public static TypeTransaction getTypeTransactionRandomSampleGenerator() {
        return new TypeTransaction().idTypeTransaction(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
