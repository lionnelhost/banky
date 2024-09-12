package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class ClientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDTO.class);
        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setIdClient("id1");
        ClientDTO clientDTO2 = new ClientDTO();
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO2.setIdClient(clientDTO1.getIdClient());
        assertThat(clientDTO1).isEqualTo(clientDTO2);
        clientDTO2.setIdClient("id2");
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO1.setIdClient(null);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
    }
}
