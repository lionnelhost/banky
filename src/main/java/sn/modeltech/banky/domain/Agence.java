package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A Agence.
 */
@Entity
@Table(name = "agence")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agence implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_agence")
    private String idAgence;

    @Column(name = "code_agence")
    private String codeAgence;

    @Column(name = "nom_agence")
    private String nomAgence;

    @Transient
    private boolean isPersisted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "agences" }, allowSetters = true)
    private Banque banque;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdAgence() {
        return this.idAgence;
    }

    public Agence idAgence(String idAgence) {
        this.setIdAgence(idAgence);
        return this;
    }

    public void setIdAgence(String idAgence) {
        this.idAgence = idAgence;
    }

    public String getCodeAgence() {
        return this.codeAgence;
    }

    public Agence codeAgence(String codeAgence) {
        this.setCodeAgence(codeAgence);
        return this;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getNomAgence() {
        return this.nomAgence;
    }

    public Agence nomAgence(String nomAgence) {
        this.setNomAgence(nomAgence);
        return this;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idAgence;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Agence setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Banque getBanque() {
        return this.banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public Agence banque(Banque banque) {
        this.setBanque(banque);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return getIdAgence() != null && getIdAgence().equals(((Agence) o).getIdAgence());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "idAgence=" + getIdAgence() +
            ", codeAgence='" + getCodeAgence() + "'" +
            ", nomAgence='" + getNomAgence() + "'" +
            "}";
    }
}
