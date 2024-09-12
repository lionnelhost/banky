package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A Canal.
 */
@Entity
@Table(name = "canal")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Canal implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_canal")
    private String idCanal;

    @Column(name = "libelle")
    private String libelle;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "canal")
    @JsonIgnoreProperties(value = { "canal", "typeTransaction", "dispositifSignature" }, allowSetters = true)
    private Set<DispositifSercurite> dispositifSercurites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdCanal() {
        return this.idCanal;
    }

    public Canal idCanal(String idCanal) {
        this.setIdCanal(idCanal);
        return this;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Canal libelle(String libelle) {
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
        return this.idCanal;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Canal setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<DispositifSercurite> getDispositifSercurites() {
        return this.dispositifSercurites;
    }

    public void setDispositifSercurites(Set<DispositifSercurite> dispositifSercurites) {
        if (this.dispositifSercurites != null) {
            this.dispositifSercurites.forEach(i -> i.setCanal(null));
        }
        if (dispositifSercurites != null) {
            dispositifSercurites.forEach(i -> i.setCanal(this));
        }
        this.dispositifSercurites = dispositifSercurites;
    }

    public Canal dispositifSercurites(Set<DispositifSercurite> dispositifSercurites) {
        this.setDispositifSercurites(dispositifSercurites);
        return this;
    }

    public Canal addDispositifSercurite(DispositifSercurite dispositifSercurite) {
        this.dispositifSercurites.add(dispositifSercurite);
        dispositifSercurite.setCanal(this);
        return this;
    }

    public Canal removeDispositifSercurite(DispositifSercurite dispositifSercurite) {
        this.dispositifSercurites.remove(dispositifSercurite);
        dispositifSercurite.setCanal(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Canal)) {
            return false;
        }
        return getIdCanal() != null && getIdCanal().equals(((Canal) o).getIdCanal());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Canal{" +
            "idCanal=" + getIdCanal() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
