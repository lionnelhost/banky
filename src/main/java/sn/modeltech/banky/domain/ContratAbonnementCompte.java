package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A ContratAbonnementCompte.
 */
@Entity
@Table(name = "contrat_abonnement_compte")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContratAbonnementCompte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_contrat")
    private String idContrat;

    @Column(name = "id_abonne")
    private String idAbonne;

    @Column(name = "id_compte_bancaire")
    private String idCompteBancaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "typeContrat", "client", "compteBancaires", "contratAbonnements", "contratAbonnementComptes" },
        allowSetters = true
    )
    private Contrat contrat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contratAbonnements", "contratAbonnementComptes" }, allowSetters = true)
    private Abonne abonne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contrat", "contratAbonnementComptes" }, allowSetters = true)
    private CompteBancaire compteBancaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContratAbonnementCompte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdContrat() {
        return this.idContrat;
    }

    public ContratAbonnementCompte idContrat(String idContrat) {
        this.setIdContrat(idContrat);
        return this;
    }

    public void setIdContrat(String idContrat) {
        this.idContrat = idContrat;
    }

    public String getIdAbonne() {
        return this.idAbonne;
    }

    public ContratAbonnementCompte idAbonne(String idAbonne) {
        this.setIdAbonne(idAbonne);
        return this;
    }

    public void setIdAbonne(String idAbonne) {
        this.idAbonne = idAbonne;
    }

    public String getIdCompteBancaire() {
        return this.idCompteBancaire;
    }

    public ContratAbonnementCompte idCompteBancaire(String idCompteBancaire) {
        this.setIdCompteBancaire(idCompteBancaire);
        return this;
    }

    public void setIdCompteBancaire(String idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public Contrat getContrat() {
        return this.contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public ContratAbonnementCompte contrat(Contrat contrat) {
        this.setContrat(contrat);
        return this;
    }

    public Abonne getAbonne() {
        return this.abonne;
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }

    public ContratAbonnementCompte abonne(Abonne abonne) {
        this.setAbonne(abonne);
        return this;
    }

    public CompteBancaire getCompteBancaire() {
        return this.compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    public ContratAbonnementCompte compteBancaire(CompteBancaire compteBancaire) {
        this.setCompteBancaire(compteBancaire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratAbonnementCompte)) {
            return false;
        }
        return getId() != null && getId().equals(((ContratAbonnementCompte) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratAbonnementCompte{" +
            "id=" + getId() +
            ", idContrat='" + getIdContrat() + "'" +
            ", idAbonne='" + getIdAbonne() + "'" +
            ", idCompteBancaire='" + getIdCompteBancaire() + "'" +
            "}";
    }
}
