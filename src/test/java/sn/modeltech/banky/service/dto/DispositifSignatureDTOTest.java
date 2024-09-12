package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class DispositifSignatureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositifSignatureDTO.class);
        DispositifSignatureDTO dispositifSignatureDTO1 = new DispositifSignatureDTO();
        dispositifSignatureDTO1.setIdDispositif("id1");
        DispositifSignatureDTO dispositifSignatureDTO2 = new DispositifSignatureDTO();
        assertThat(dispositifSignatureDTO1).isNotEqualTo(dispositifSignatureDTO2);
        dispositifSignatureDTO2.setIdDispositif(dispositifSignatureDTO1.getIdDispositif());
        assertThat(dispositifSignatureDTO1).isEqualTo(dispositifSignatureDTO2);
        dispositifSignatureDTO2.setIdDispositif("id2");
        assertThat(dispositifSignatureDTO1).isNotEqualTo(dispositifSignatureDTO2);
        dispositifSignatureDTO1.setIdDispositif(null);
        assertThat(dispositifSignatureDTO1).isNotEqualTo(dispositifSignatureDTO2);
    }
}
