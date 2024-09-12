package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.TypeClient} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeClientDTO implements Serializable {

    private String idTypeClient;

    private String libelle;

    public String getIdTypeClient() {
        return idTypeClient;
    }

    public void setIdTypeClient(String idTypeClient) {
        this.idTypeClient = idTypeClient;
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
        if (!(o instanceof TypeClientDTO)) {
            return false;
        }

        TypeClientDTO typeClientDTO = (TypeClientDTO) o;
        if (this.idTypeClient == null) {
            return false;
        }
        return Objects.equals(this.idTypeClient, typeClientDTO.idTypeClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idTypeClient);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeClientDTO{" +
            "idTypeClient='" + getIdTypeClient() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
