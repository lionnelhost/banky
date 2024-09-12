package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Contrat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContratDTO implements Serializable {

    private String idContrat;

    private Instant dateValidite;

    private TypeContratDTO typeContrat;

    public String getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(String idContrat) {
        this.idContrat = idContrat;
    }

    public Instant getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(Instant dateValidite) {
        this.dateValidite = dateValidite;
    }

    public TypeContratDTO getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContratDTO typeContrat) {
        this.typeContrat = typeContrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratDTO)) {
            return false;
        }

        ContratDTO contratDTO = (ContratDTO) o;
        if (this.idContrat == null) {
            return false;
        }
        return Objects.equals(this.idContrat, contratDTO.idContrat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idContrat);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratDTO{" +
            "idContrat='" + getIdContrat() + "'" +
            ", dateValidite='" + getDateValidite() + "'" +
            ", typeContrat=" + getTypeContrat() +
            "}";
    }
}
