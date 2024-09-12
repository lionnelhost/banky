package sn.modeltech.banky.domain;

import java.util.UUID;

public class CompteBancaireTestSamples {

    public static CompteBancaire getCompteBancaireSample1() {
        return new CompteBancaire()
            .idCompteBancaire("idCompteBancaire1")
            .age("age1")
            .ncp("ncp1")
            .sde("sde1")
            .sin("sin1")
            .soldeDisponible("soldeDisponible1")
            .rib("rib1");
    }

    public static CompteBancaire getCompteBancaireSample2() {
        return new CompteBancaire()
            .idCompteBancaire("idCompteBancaire2")
            .age("age2")
            .ncp("ncp2")
            .sde("sde2")
            .sin("sin2")
            .soldeDisponible("soldeDisponible2")
            .rib("rib2");
    }

    public static CompteBancaire getCompteBancaireRandomSampleGenerator() {
        return new CompteBancaire()
            .idCompteBancaire(UUID.randomUUID().toString())
            .age(UUID.randomUUID().toString())
            .ncp(UUID.randomUUID().toString())
            .sde(UUID.randomUUID().toString())
            .sin(UUID.randomUUID().toString())
            .soldeDisponible(UUID.randomUUID().toString())
            .rib(UUID.randomUUID().toString());
    }
}
