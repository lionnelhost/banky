package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.TypeTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeTransactionDTO implements Serializable {

    private String idTypeTransaction;

    private String libelle;

    public String getIdTypeTransaction() {
        return idTypeTransaction;
    }

    public void setIdTypeTransaction(String idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeTransactionDTO)) {
            return false;
        }

        TypeTransactionDTO typeTransactionDTO = (TypeTransactionDTO) o;
        if (this.idTypeTransaction == null) {
            return false;
        }
        return Objects.equals(this.idTypeTransaction, typeTransactionDTO.idTypeTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idTypeTransaction);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeTransactionDTO{" +
            "idTypeTransaction='" + getIdTypeTransaction() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
