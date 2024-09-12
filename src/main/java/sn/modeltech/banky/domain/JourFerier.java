package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A JourFerier.
 */
@Entity
@Table(name = "jour_ferier")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourFerier implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_jour_ferie")
    private String idJourFerie;

    @Column(name = "libelle")
    private String libelle;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdJourFerie() {
        return this.idJourFerie;
    }

    public JourFerier idJourFerie(String idJourFerie) {
        this.setIdJourFerie(idJourFerie);
        return this;
    }

    public void setIdJourFerie(String idJourFerie) {
        this.idJourFerie = idJourFerie;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public JourFerier libelle(String libelle) {
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
        return this.idJourFerie;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public JourFerier setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourFerier)) {
            return false;
        }
        return getIdJourFerie() != null && getIdJourFerie().equals(((JourFerier) o).getIdJourFerie());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourFerier{" +
            "idJourFerie=" + getIdJourFerie() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
