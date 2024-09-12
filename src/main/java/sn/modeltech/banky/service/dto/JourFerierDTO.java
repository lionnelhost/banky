package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.JourFerier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourFerierDTO implements Serializable {

    private String idJourFerie;

    private String libelle;

    public String getIdJourFerie() {
        return idJourFerie;
    }

    public void setIdJourFerie(String idJourFerie) {
        this.idJourFerie = idJourFerie;
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
        if (!(o instanceof JourFerierDTO)) {
            return false;
        }

        JourFerierDTO jourFerierDTO = (JourFerierDTO) o;
        if (this.idJourFerie == null) {
            return false;
        }
        return Objects.equals(this.idJourFerie, jourFerierDTO.idJourFerie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idJourFerie);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourFerierDTO{" +
            "idJourFerie='" + getIdJourFerie() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
