package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A ParametrageNotification.
 */
@Entity
@Table(name = "parametrage_notification")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParametrageNotification implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_param_notif")
    private String idParamNotif;

    @Column(name = "objet_notif")
    private String objetNotif;

    @Column(name = "type_notif")
    private String typeNotif;

    @Column(name = "contenu")
    private String contenu;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdParamNotif() {
        return this.idParamNotif;
    }

    public ParametrageNotification idParamNotif(String idParamNotif) {
        this.setIdParamNotif(idParamNotif);
        return this;
    }

    public void setIdParamNotif(String idParamNotif) {
        this.idParamNotif = idParamNotif;
    }

    public String getObjetNotif() {
        return this.objetNotif;
    }

    public ParametrageNotification objetNotif(String objetNotif) {
        this.setObjetNotif(objetNotif);
        return this;
    }

    public void setObjetNotif(String objetNotif) {
        this.objetNotif = objetNotif;
    }

    public String getTypeNotif() {
        return this.typeNotif;
    }

    public ParametrageNotification typeNotif(String typeNotif) {
        this.setTypeNotif(typeNotif);
        return this;
    }

    public void setTypeNotif(String typeNotif) {
        this.typeNotif = typeNotif;
    }

    public String getContenu() {
        return this.contenu;
    }

    public ParametrageNotification contenu(String contenu) {
        this.setContenu(contenu);
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idParamNotif;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ParametrageNotification setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametrageNotification)) {
            return false;
        }
        return getIdParamNotif() != null && getIdParamNotif().equals(((ParametrageNotification) o).getIdParamNotif());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametrageNotification{" +
            "idParamNotif=" + getIdParamNotif() +
            ", objetNotif='" + getObjetNotif() + "'" +
            ", typeNotif='" + getTypeNotif() + "'" +
            ", contenu='" + getContenu() + "'" +
            "}";
    }
}
