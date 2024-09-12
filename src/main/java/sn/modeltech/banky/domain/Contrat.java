package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A Contrat.
 */
@Entity
@Table(name = "contrat")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contrat implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_contrat")
    private String idContrat;

    @Column(name = "date_validite")
    private Instant dateValidite;

    @Transient
    private boolean isPersisted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contrats" }, allowSetters = true)
    private TypeContrat typeContrat;

    @JsonIgnoreProperties(value = { "contrat", "typeClient" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "contrat")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrat")
    @JsonIgnoreProperties(value = { "contrat", "contratAbonnementComptes" }, allowSetters = true)
    private Set<CompteBancaire> compteBancaires = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrat")
    @JsonIgnoreProperties(value = { "contrat", "abonne" }, allowSetters = true)
    private Set<ContratAbonnement> contratAbonnements = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrat")
    @JsonIgnoreProperties(value = { "contrat", "abonne", "compteBancaire" }, allowSetters = true)
    private Set<ContratAbonnementCompte> contratAbonnementComptes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdContrat() {
        return this.idContrat;
    }

    public Contrat idContrat(String idContrat) {
        this.setIdContrat(idContrat);
        return this;
    }

    public void setIdContrat(String idContrat) {
        this.idContrat = idContrat;
    }

    public Instant getDateValidite() {
        return this.dateValidite;
    }

    public Contrat dateValidite(Instant dateValidite) {
        this.setDateValidite(dateValidite);
        return this;
    }

    public void setDateValidite(Instant dateValidite) {
        this.dateValidite = dateValidite;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idContrat;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Contrat setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public TypeContrat getTypeContrat() {
        return this.typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Contrat typeContrat(TypeContrat typeContrat) {
        this.setTypeContrat(typeContrat);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        if (this.client != null) {
            this.client.setContrat(null);
        }
        if (client != null) {
            client.setContrat(this);
        }
        this.client = client;
    }

    public Contrat client(Client client) {
        this.setClient(client);
        return this;
    }

    public Set<CompteBancaire> getCompteBancaires() {
        return this.compteBancaires;
    }

    public void setCompteBancaires(Set<CompteBancaire> compteBancaires) {
        if (this.compteBancaires != null) {
            this.compteBancaires.forEach(i -> i.setContrat(null));
        }
        if (compteBancaires != null) {
            compteBancaires.forEach(i -> i.setContrat(this));
        }
        this.compteBancaires = compteBancaires;
    }

    public Contrat compteBancaires(Set<CompteBancaire> compteBancaires) {
        this.setCompteBancaires(compteBancaires);
        return this;
    }

    public Contrat addCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaires.add(compteBancaire);
        compteBancaire.setContrat(this);
        return this;
    }

    public Contrat removeCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaires.remove(compteBancaire);
        compteBancaire.setContrat(null);
        return this;
    }

    public Set<ContratAbonnement> getContratAbonnements() {
        return this.contratAbonnements;
    }

    public void setContratAbonnements(Set<ContratAbonnement> contratAbonnements) {
        if (this.contratAbonnements != null) {
            this.contratAbonnements.forEach(i -> i.setContrat(null));
        }
        if (contratAbonnements != null) {
            contratAbonnements.forEach(i -> i.setContrat(this));
        }
        this.contratAbonnements = contratAbonnements;
    }

    public Contrat contratAbonnements(Set<ContratAbonnement> contratAbonnements) {
        this.setContratAbonnements(contratAbonnements);
        return this;
    }

    public Contrat addContratAbonnement(ContratAbonnement contratAbonnement) {
        this.contratAbonnements.add(contratAbonnement);
        contratAbonnement.setContrat(this);
        return this;
    }

    public Contrat removeContratAbonnement(ContratAbonnement contratAbonnement) {
        this.contratAbonnements.remove(contratAbonnement);
        contratAbonnement.setContrat(null);
        return this;
    }

    public Set<ContratAbonnementCompte> getContratAbonnementComptes() {
        return this.contratAbonnementComptes;
    }

    public void setContratAbonnementComptes(Set<ContratAbonnementCompte> contratAbonnementComptes) {
        if (this.contratAbonnementComptes != null) {
            this.contratAbonnementComptes.forEach(i -> i.setContrat(null));
        }
        if (contratAbonnementComptes != null) {
            contratAbonnementComptes.forEach(i -> i.setContrat(this));
        }
        this.contratAbonnementComptes = contratAbonnementComptes;
    }

    public Contrat contratAbonnementComptes(Set<ContratAbonnementCompte> contratAbonnementComptes) {
        this.setContratAbonnementComptes(contratAbonnementComptes);
        return this;
    }

    public Contrat addContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        this.contratAbonnementComptes.add(contratAbonnementCompte);
        contratAbonnementCompte.setContrat(this);
        return this;
    }

    public Contrat removeContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        this.contratAbonnementComptes.remove(contratAbonnementCompte);
        contratAbonnementCompte.setContrat(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrat)) {
            return false;
        }
        return getIdContrat() != null && getIdContrat().equals(((Contrat) o).getIdContrat());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contrat{" +
            "idContrat=" + getIdContrat() +
            ", dateValidite='" + getDateValidite() + "'" +
            "}";
    }
}
