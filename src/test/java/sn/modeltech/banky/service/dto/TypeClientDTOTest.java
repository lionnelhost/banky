package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class TypeClientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeClientDTO.class);
        TypeClientDTO typeClientDTO1 = new TypeClientDTO();
        typeClientDTO1.setIdTypeClient("id1");
        TypeClientDTO typeClientDTO2 = new TypeClientDTO();
        assertThat(typeClientDTO1).isNotEqualTo(typeClientDTO2);
        typeClientDTO2.setIdTypeClient(typeClientDTO1.getIdTypeClient());
        assertThat(typeClientDTO1).isEqualTo(typeClientDTO2);
        typeClientDTO2.setIdTypeClient("id2");
        assertThat(typeClientDTO1).isNotEqualTo(typeClientDTO2);
        typeClientDTO1.setIdTypeClient(null);
        assertThat(typeClientDTO1).isNotEqualTo(typeClientDTO2);
    }
}
