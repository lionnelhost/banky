package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A TypeContrat.
 */
@Entity
@Table(name = "type_contrat")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeContrat implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_type_contrat")
    private String idTypeContrat;

    @Column(name = "libelle")
    private String libelle;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeContrat")
    @JsonIgnoreProperties(
        value = { "typeContrat", "client", "compteBancaires", "contratAbonnements", "contratAbonnementComptes" },
        allowSetters = true
    )
    private Set<Contrat> contrats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdTypeContrat() {
        return this.idTypeContrat;
    }

    public TypeContrat idTypeContrat(String idTypeContrat) {
        this.setIdTypeContrat(idTypeContrat);
        return this;
    }

    public void setIdTypeContrat(String idTypeContrat) {
        this.idTypeContrat = idTypeContrat;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeContrat libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idTypeContrat;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public TypeContrat setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Contrat> getContrats() {
        return this.contrats;
    }

    public void setContrats(Set<Contrat> contrats) {
        if (this.contrats != null) {
            this.contrats.forEach(i -> i.setTypeContrat(null));
        }
        if (contrats != null) {
            contrats.forEach(i -> i.setTypeContrat(this));
        }
        this.contrats = contrats;
    }

    public TypeContrat contrats(Set<Contrat> contrats) {
        this.setContrats(contrats);
        return this;
    }

    public TypeContrat addContrat(Contrat contrat) {
        this.contrats.add(contrat);
        contrat.setTypeContrat(this);
        return this;
    }

    public TypeContrat removeContrat(Contrat contrat) {
        this.contrats.remove(contrat);
        contrat.setTypeContrat(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeContrat)) {
            return false;
        }
        return getIdTypeContrat() != null && getIdTypeContrat().equals(((TypeContrat) o).getIdTypeContrat());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeContrat{" +
            "idTypeContrat=" + getIdTypeContrat() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
