package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Canal} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CanalDTO implements Serializable {

    private String idCanal;

    private String libelle;

    public String getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
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
        if (!(o instanceof CanalDTO)) {
            return false;
        }

        CanalDTO canalDTO = (CanalDTO) o;
        if (this.idCanal == null) {
            return false;
        }
        return Objects.equals(this.idCanal, canalDTO.idCanal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCanal);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CanalDTO{" +
            "idCanal='" + getIdCanal() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
