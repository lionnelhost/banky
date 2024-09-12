package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static sn.modeltech.banky.domain.ClientTestSamples.*;
import static sn.modeltech.banky.domain.ContratTestSamples.*;
import static sn.modeltech.banky.domain.TypeClientTestSamples.*;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setIdClient(client1.getIdClient());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void contratTest() {
        Client client = getClientRandomSampleGenerator();
        Contrat contratBack = getContratRandomSampleGenerator();

        client.setContrat(contratBack);
        assertThat(client.getContrat()).isEqualTo(contratBack);

        client.contrat(null);
        assertThat(client.getContrat()).isNull();
    }

    @Test
    void typeClientTest() {
        Client client = getClientRandomSampleGenerator();
        TypeClient typeClientBack = getTypeClientRandomSampleGenerator();

        client.setTypeClient(typeClientBack);
        assertThat(client.getTypeClient()).isEqualTo(typeClientBack);

        client.typeClient(null);
        assertThat(client.getTypeClient()).isNull();
    }
}
