package sn.modeltech.banky.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.modeltech.banky.web.rest.TestUtil;

class TypeTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTransactionDTO.class);
        TypeTransactionDTO typeTransactionDTO1 = new TypeTransactionDTO();
        typeTransactionDTO1.setIdTypeTransaction("id1");
        TypeTransactionDTO typeTransactionDTO2 = new TypeTransactionDTO();
        assertThat(typeTransactionDTO1).isNotEqualTo(typeTransactionDTO2);
        typeTransactionDTO2.setIdTypeTransaction(typeTransactionDTO1.getIdTypeTransaction());
        assertThat(typeTransactionDTO1).isEqualTo(typeTransactionDTO2);
        typeTransactionDTO2.setIdTypeTransaction("id2");
        assertThat(typeTransactionDTO1).isNotEqualTo(typeTransactionDTO2);
        typeTransactionDTO1.setIdTypeTransaction(null);
        assertThat(typeTransactionDTO1).isNotEqualTo(typeTransactionDTO2);
    }
}
