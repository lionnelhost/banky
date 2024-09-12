package sn.modeltech.banky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DispositifSercuriteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DispositifSercurite getDispositifSercuriteSample1() {
        return new DispositifSercurite().id(1L).idCanal("idCanal1").idTypeTransaction("idTypeTransaction1").idDispositif("idDispositif1");
    }

    public static DispositifSercurite getDispositifSercuriteSample2() {
        return new DispositifSercurite().id(2L).idCanal("idCanal2").idTypeTransaction("idTypeTransaction2").idDispositif("idDispositif2");
    }

    public static DispositifSercurite getDispositifSercuriteRandomSampleGenerator() {
        return new DispositifSercurite()
            .id(longCount.incrementAndGet())
            .idCanal(UUID.randomUUID().toString())
            .idTypeTransaction(UUID.randomUUID().toString())
            .idDispositif(UUID.randomUUID().toString());
    }
}
