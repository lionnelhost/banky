package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class DispositifSercuriteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositifSercuriteDTO.class);
        DispositifSercuriteDTO dispositifSercuriteDTO1 = new DispositifSercuriteDTO();
        dispositifSercuriteDTO1.setId(1L);
        DispositifSercuriteDTO dispositifSercuriteDTO2 = new DispositifSercuriteDTO();
        assertThat(dispositifSercuriteDTO1).isNotEqualTo(dispositifSercuriteDTO2);
        dispositifSercuriteDTO2.setId(dispositifSercuriteDTO1.getId());
        assertThat(dispositifSercuriteDTO1).isEqualTo(dispositifSercuriteDTO2);
        dispositifSercuriteDTO2.setId(2L);
        assertThat(dispositifSercuriteDTO1).isNotEqualTo(dispositifSercuriteDTO2);
        dispositifSercuriteDTO1.setId(null);
        assertThat(dispositifSercuriteDTO1).isNotEqualTo(dispositifSercuriteDTO2);
    }
}
