package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class TypeContratDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeContratDTO.class);
        TypeContratDTO typeContratDTO1 = new TypeContratDTO();
        typeContratDTO1.setIdTypeContrat("id1");
        TypeContratDTO typeContratDTO2 = new TypeContratDTO();
        assertThat(typeContratDTO1).isNotEqualTo(typeContratDTO2);
        typeContratDTO2.setIdTypeContrat(typeContratDTO1.getIdTypeContrat());
        assertThat(typeContratDTO1).isEqualTo(typeContratDTO2);
        typeContratDTO2.setIdTypeContrat("id2");
        assertThat(typeContratDTO1).isNotEqualTo(typeContratDTO2);
        typeContratDTO1.setIdTypeContrat(null);
        assertThat(typeContratDTO1).isNotEqualTo(typeContratDTO2);
    }
}
