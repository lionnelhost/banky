package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ClientTestSamples.*;
import static sn.modeltech.banky.domain.TypeClientTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class TypeClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeClient.class);
        TypeClient typeClient1 = getTypeClientSample1();
        TypeClient typeClient2 = new TypeClient();
        assertThat(typeClient1).isNotEqualTo(typeClient2);

        typeClient2.setIdTypeClient(typeClient1.getIdTypeClient());
        assertThat(typeClient1).isEqualTo(typeClient2);

        typeClient2 = getTypeClientSample2();
        assertThat(typeClient1).isNotEqualTo(typeClient2);
    }

    @Test
    void clientTest() {
        TypeClient typeClient = getTypeClientRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        typeClient.addClient(clientBack);
        assertThat(typeClient.getClients()).containsOnly(clientBack);
        assertThat(clientBack.getTypeClient()).isEqualTo(typeClient);

        typeClient.removeClient(clientBack);
        assertThat(typeClient.getClients()).doesNotContain(clientBack);
        assertThat(clientBack.getTypeClient()).isNull();

        typeClient.clients(new HashSet<>(Set.of(clientBack)));
        assertThat(typeClient.getClients()).containsOnly(clientBack);
        assertThat(clientBack.getTypeClient()).isEqualTo(typeClient);

        typeClient.setClients(new HashSet<>());
        assertThat(typeClient.getClients()).doesNotContain(clientBack);
        assertThat(clientBack.getTypeClient()).isNull();
    }
}
