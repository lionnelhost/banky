package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.ContratAbonnement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContratAbonnementDTO implements Serializable {

    private Long id;

    private String idContrat;

    private String idAbonne;

    private ContratDTO contrat;

    private AbonneDTO abonne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(String idContrat) {
        this.idContrat = idContrat;
    }

    public String getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(String idAbonne) {
        this.idAbonne = idAbonne;
    }

    public ContratDTO getContrat() {
        return contrat;
    }

    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }

    public AbonneDTO getAbonne() {
        return abonne;
    }

    public void setAbonne(AbonneDTO abonne) {
        this.abonne = abonne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratAbonnementDTO)) {
            return false;
        }

        ContratAbonnementDTO contratAbonnementDTO = (ContratAbonnementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contratAbonnementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratAbonnementDTO{" +
            "id=" + getId() +
            ", idContrat='" + getIdContrat() + "'" +
            ", idAbonne='" + getIdAbonne() + "'" +
            ", contrat=" + getContrat() +
            ", abonne=" + getAbonne() +
            "}";
    }
}
