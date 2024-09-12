package sn.modeltech.banky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContratAbonnementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContratAbonnement getContratAbonnementSample1() {
        return new ContratAbonnement().id(1L).idContrat("idContrat1").idAbonne("idAbonne1");
    }

    public static ContratAbonnement getContratAbonnementSample2() {
        return new ContratAbonnement().id(2L).idContrat("idContrat2").idAbonne("idAbonne2");
    }

    public static ContratAbonnement getContratAbonnementRandomSampleGenerator() {
        return new ContratAbonnement()
            .id(longCount.incrementAndGet())
            .idContrat(UUID.randomUUID().toString())
            .idAbonne(UUID.randomUUID().toString());
    }
}
