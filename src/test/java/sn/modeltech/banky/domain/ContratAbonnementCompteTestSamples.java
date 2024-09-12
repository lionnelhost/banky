package sn.modeltech.banky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContratAbonnementCompteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContratAbonnementCompte getContratAbonnementCompteSample1() {
        return new ContratAbonnementCompte().id(1L).idContrat("idContrat1").idAbonne("idAbonne1").idCompteBancaire("idCompteBancaire1");
    }

    public static ContratAbonnementCompte getContratAbonnementCompteSample2() {
        return new ContratAbonnementCompte().id(2L).idContrat("idContrat2").idAbonne("idAbonne2").idCompteBancaire("idCompteBancaire2");
    }

    public static ContratAbonnementCompte getContratAbonnementCompteRandomSampleGenerator() {
        return new ContratAbonnementCompte()
            .id(longCount.incrementAndGet())
            .idContrat(UUID.randomUUID().toString())
            .idAbonne(UUID.randomUUID().toString())
            .idCompteBancaire(UUID.randomUUID().toString());
    }
}
