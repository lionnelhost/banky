package sn.modeltech.banky.domain;

import java.util.UUID;

public class ClientTestSamples {

    public static Client getClientSample1() {
        return new Client()
            .idClient("idClient1")
            .indiceClient("indiceClient1")
            .nomClient("nomClient1")
            .prenomClient("prenomClient1")
            .raisonSociale("raisonSociale1")
            .telephone("telephone1")
            .email("email1");
    }

    public static Client getClientSample2() {
        return new Client()
            .idClient("idClient2")
            .indiceClient("indiceClient2")
            .nomClient("nomClient2")
            .prenomClient("prenomClient2")
            .raisonSociale("raisonSociale2")
            .telephone("telephone2")
            .email("email2");
    }

    public static Client getClientRandomSampleGenerator() {
        return new Client()
            .idClient(UUID.randomUUID().toString())
            .indiceClient(UUID.randomUUID().toString())
            .nomClient(UUID.randomUUID().toString())
            .prenomClient(UUID.randomUUID().toString())
            .raisonSociale(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
