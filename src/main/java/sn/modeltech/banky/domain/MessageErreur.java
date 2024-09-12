package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A MessageErreur.
 */
@Entity
@Table(name = "message_erreur")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageErreur implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_message_erreur")
    private String idMessageErreur;

    @Column(name = "code_erreur")
    private String codeErreur;

    @Column(name = "description")
    private String description;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdMessageErreur() {
        return this.idMessageErreur;
    }

    public MessageErreur idMessageErreur(String idMessageErreur) {
        this.setIdMessageErreur(idMessageErreur);
        return this;
    }

    public void setIdMessageErreur(String idMessageErreur) {
        this.idMessageErreur = idMessageErreur;
    }

    public String getCodeErreur() {
        return this.codeErreur;
    }

    public MessageErreur codeErreur(String codeErreur) {
        this.setCodeErreur(codeErreur);
        return this;
    }

    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }

    public String getDescription() {
        return this.description;
    }

    public MessageErreur description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idMessageErreur;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public MessageErreur setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageErreur)) {
            return false;
        }
        return getIdMessageErreur() != null && getIdMessageErreur().equals(((MessageErreur) o).getIdMessageErreur());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageErreur{" +
            "idMessageErreur=" + getIdMessageErreur() +
            ", codeErreur='" + getCodeErreur() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
