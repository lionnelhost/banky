package sn.modeltech.banky.domain;

import java.util.UUID;

public class DispositifSignatureTestSamples {

    public static DispositifSignature getDispositifSignatureSample1() {
        return new DispositifSignature().idDispositif("idDispositif1").libelle("libelle1");
    }

    public static DispositifSignature getDispositifSignatureSample2() {
        return new DispositifSignature().idDispositif("idDispositif2").libelle("libelle2");
    }

    public static DispositifSignature getDispositifSignatureRandomSampleGenerator() {
        return new DispositifSignature().idDispositif(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
