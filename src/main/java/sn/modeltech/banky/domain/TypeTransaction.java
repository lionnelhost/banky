package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A TypeTransaction.
 */
@Entity
@Table(name = "type_transaction")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeTransaction implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_type_transaction")
    private String idTypeTransaction;

    @Column(name = "libelle")
    private String libelle;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeTransaction")
    @JsonIgnoreProperties(value = { "canal", "typeTransaction", "dispositifSignature" }, allowSetters = true)
    private Set<DispositifSercurite> dispositifSercurites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdTypeTransaction() {
        return this.idTypeTransaction;
    }

    public TypeTransaction idTypeTransaction(String idTypeTransaction) {
        this.setIdTypeTransaction(idTypeTransaction);
        return this;
    }

    public void setIdTypeTransaction(String idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeTransaction libelle(String libelle) {
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
        return this.idTypeTransaction;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public TypeTransaction setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<DispositifSercurite> getDispositifSercurites() {
        return this.dispositifSercurites;
    }

    public void setDispositifSercurites(Set<DispositifSercurite> dispositifSercurites) {
        if (this.dispositifSercurites != null) {
            this.dispositifSercurites.forEach(i -> i.setTypeTransaction(null));
        }
        if (dispositifSercurites != null) {
            dispositifSercurites.forEach(i -> i.setTypeTransaction(this));
        }
        this.dispositifSercurites = dispositifSercurites;
    }

    public TypeTransaction dispositifSercurites(Set<DispositifSercurite> dispositifSercurites) {
        this.setDispositifSercurites(dispositifSercurites);
        return this;
    }

    public TypeTransaction addDispositifSercurite(DispositifSercurite dispositifSercurite) {
        this.dispositifSercurites.add(dispositifSercurite);
        dispositifSercurite.setTypeTransaction(this);
        return this;
    }

    public TypeTransaction removeDispositifSercurite(DispositifSercurite dispositifSercurite) {
        this.dispositifSercurites.remove(dispositifSercurite);
        dispositifSercurite.setTypeTransaction(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeTransaction)) {
            return false;
        }
        return getIdTypeTransaction() != null && getIdTypeTransaction().equals(((TypeTransaction) o).getIdTypeTransaction());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeTransaction{" +
            "idTypeTransaction=" + getIdTypeTransaction() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
