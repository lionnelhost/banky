package sn.modeltech.banky.domain;

import java.util.UUID;

public class AbonneTestSamples {

    public static Abonne getAbonneSample1() {
        return new Abonne()
            .idAbonne("idAbonne1")
            .indiceClient("indiceClient1")
            .nomAbonne("nomAbonne1")
            .prenomAbonne("prenomAbonne1")
            .telephone("telephone1")
            .email("email1")
            .numPieceIdentite("numPieceIdentite1")
            .identifiant("identifiant1");
    }

    public static Abonne getAbonneSample2() {
        return new Abonne()
            .idAbonne("idAbonne2")
            .indiceClient("indiceClient2")
            .nomAbonne("nomAbonne2")
            .prenomAbonne("prenomAbonne2")
            .telephone("telephone2")
            .email("email2")
            .numPieceIdentite("numPieceIdentite2")
            .identifiant("identifiant2");
    }

    public static Abonne getAbonneRandomSampleGenerator() {
        return new Abonne()
            .idAbonne(UUID.randomUUID().toString())
            .indiceClient(UUID.randomUUID().toString())
            .nomAbonne(UUID.randomUUID().toString())
            .prenomAbonne(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .numPieceIdentite(UUID.randomUUID().toString())
            .identifiant(UUID.randomUUID().toString());
    }
}
