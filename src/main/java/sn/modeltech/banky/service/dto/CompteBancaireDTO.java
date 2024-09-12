package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.modeltech.banky.domain.enumeration.StatutCompteBancaire;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.CompteBancaire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteBancaireDTO implements Serializable {

    private String idCompteBancaire;

    private String age;

    private String ncp;

    private String sde;

    private String sin;

    private String soldeDisponible;

    private String rib;

    private StatutCompteBancaire status;

    private ContratDTO contrat;

    public String getIdCompteBancaire() {
        return idCompteBancaire;
    }

    public void setIdCompteBancaire(String idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNcp() {
        return ncp;
    }

    public void setNcp(String ncp) {
        this.ncp = ncp;
    }

    public String getSde() {
        return sde;
    }

    public void setSde(String sde) {
        this.sde = sde;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getSoldeDisponible() {
        return soldeDisponible;
    }

    public void setSoldeDisponible(String soldeDisponible) {
        this.soldeDisponible = soldeDisponible;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public StatutCompteBancaire getStatus() {
        return status;
    }

    public void setStatus(StatutCompteBancaire status) {
        this.status = status;
    }

    public ContratDTO getContrat() {
        return contrat;
    }

    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteBancaireDTO)) {
            return false;
        }

        CompteBancaireDTO compteBancaireDTO = (CompteBancaireDTO) o;
        if (this.idCompteBancaire == null) {
            return false;
        }
        return Objects.equals(this.idCompteBancaire, compteBancaireDTO.idCompteBancaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompteBancaire);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteBancaireDTO{" +
            "idCompteBancaire='" + getIdCompteBancaire() + "'" +
            ", age='" + getAge() + "'" +
            ", ncp='" + getNcp() + "'" +
            ", sde='" + getSde() + "'" +
            ", sin='" + getSin() + "'" +
            ", soldeDisponible='" + getSoldeDisponible() + "'" +
            ", rib='" + getRib() + "'" +
            ", status='" + getStatus() + "'" +
            ", contrat=" + getContrat() +
            "}";
    }
}
