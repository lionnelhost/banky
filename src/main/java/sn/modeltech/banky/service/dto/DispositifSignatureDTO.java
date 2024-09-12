package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.DispositifSignature} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DispositifSignatureDTO implements Serializable {

    private String idDispositif;

    private String libelle;

    public String getIdDispositif() {
        return idDispositif;
    }

    public void setIdDispositif(String idDispositif) {
        this.idDispositif = idDispositif;
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
        if (!(o instanceof DispositifSignatureDTO)) {
            return false;
        }

        DispositifSignatureDTO dispositifSignatureDTO = (DispositifSignatureDTO) o;
        if (this.idDispositif == null) {
            return false;
        }
        return Objects.equals(this.idDispositif, dispositifSignatureDTO.idDispositif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idDispositif);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositifSignatureDTO{" +
            "idDispositif='" + getIdDispositif() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
