package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.TypeContrat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeContratDTO implements Serializable {

    private String idTypeContrat;

    private String libelle;

    public String getIdTypeContrat() {
        return idTypeContrat;
    }

    public void setIdTypeContrat(String idTypeContrat) {
        this.idTypeContrat = idTypeContrat;
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
        if (!(o instanceof TypeContratDTO)) {
            return false;
        }

        TypeContratDTO typeContratDTO = (TypeContratDTO) o;
        if (this.idTypeContrat == null) {
            return false;
        }
        return Objects.equals(this.idTypeContrat, typeContratDTO.idTypeContrat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idTypeContrat);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeContratDTO{" +
            "idTypeContrat='" + getIdTypeContrat() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
