package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A DispositifSignature.
 */
@Entity
@Table(name = "dispositif_signature")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DispositifSignature implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_dispositif")
    private String idDispositif;

    @Column(name = "libelle")
    private String libelle;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositifSignature")
    @JsonIgnoreProperties(value = { "canal", "typeTransaction", "dispositifSignature" }, allowSetters = true)
    private Set<DispositifSercurite> dispositifSercurites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdDispositif() {
        return this.idDispositif;
    }

    public DispositifSignature idDispositif(String idDispositif) {
        this.setIdDispositif(idDispositif);
        return this;
    }

    public void setIdDispositif(String idDispositif) {
        this.idDispositif = idDispositif;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public DispositifSignature libelle(String libelle) {
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
        return this.idDispositif;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public DispositifSignature setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<DispositifSercurite> getDispositifSercurites() {
        return this.dispositifSercurites;
    }

    public void setDispositifSercurites(Set<DispositifSercurite> dispositifSercurites) {
        if (this.dispositifSercurites != null) {
            this.dispositifSercurites.forEach(i -> i.setDispositifSignature(null));
        }
        if (dispositifSercurites != null) {
            dispositifSercurites.forEach(i -> i.setDispositifSignature(this));
        }
        this.dispositifSercurites = dispositifSercurites;
    }

    public DispositifSignature dispositifSercurites(Set<DispositifSercurite> dispositifSercurites) {
        this.setDispositifSercurites(dispositifSercurites);
        return this;
    }

    public DispositifSignature addDispositifSercurite(DispositifSercurite dispositifSercurite) {
        this.dispositifSercurites.add(dispositifSercurite);
        dispositifSercurite.setDispositifSignature(this);
        return this;
    }

    public DispositifSignature removeDispositifSercurite(DispositifSercurite dispositifSercurite) {
        this.dispositifSercurites.remove(dispositifSercurite);
        dispositifSercurite.setDispositifSignature(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositifSignature)) {
            return false;
        }
        return getIdDispositif() != null && getIdDispositif().equals(((DispositifSignature) o).getIdDispositif());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositifSignature{" +
            "idDispositif=" + getIdDispositif() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
