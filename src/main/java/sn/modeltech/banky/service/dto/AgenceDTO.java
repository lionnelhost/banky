package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Agence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenceDTO implements Serializable {

    private String idAgence;

    private String codeAgence;

    private String nomAgence;

    private BanqueDTO banque;

    public String getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(String idAgence) {
        this.idAgence = idAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    public BanqueDTO getBanque() {
        return banque;
    }

    public void setBanque(BanqueDTO banque) {
        this.banque = banque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgenceDTO)) {
            return false;
        }

        AgenceDTO agenceDTO = (AgenceDTO) o;
        if (this.idAgence == null) {
            return false;
        }
        return Objects.equals(this.idAgence, agenceDTO.idAgence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idAgence);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenceDTO{" +
            "idAgence='" + getIdAgence() + "'" +
            ", codeAgence='" + getCodeAgence() + "'" +
            ", nomAgence='" + getNomAgence() + "'" +
            ", banque=" + getBanque() +
            "}";
    }
}
