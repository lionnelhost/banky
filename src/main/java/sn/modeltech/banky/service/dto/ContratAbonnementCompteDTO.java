package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.ContratAbonnementCompte} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContratAbonnementCompteDTO implements Serializable {

    private Long id;

    private String idContrat;

    private String idAbonne;

    private String idCompteBancaire;

    private ContratDTO contrat;

    private AbonneDTO abonne;

    private CompteBancaireDTO compteBancaire;

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

    public String getIdCompteBancaire() {
        return idCompteBancaire;
    }

    public void setIdCompteBancaire(String idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
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

    public CompteBancaireDTO getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaireDTO compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratAbonnementCompteDTO)) {
            return false;
        }

        ContratAbonnementCompteDTO contratAbonnementCompteDTO = (ContratAbonnementCompteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contratAbonnementCompteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratAbonnementCompteDTO{" +
            "id=" + getId() +
            ", idContrat='" + getIdContrat() + "'" +
            ", idAbonne='" + getIdAbonne() + "'" +
            ", idCompteBancaire='" + getIdCompteBancaire() + "'" +
            ", contrat=" + getContrat() +
            ", abonne=" + getAbonne() +
            ", compteBancaire=" + getCompteBancaire() +
            "}";
    }
}
