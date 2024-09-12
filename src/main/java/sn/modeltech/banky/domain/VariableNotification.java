package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A VariableNotification.
 */
@Entity
@Table(name = "variable_notification")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VariableNotification implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_var_notification")
    private String idVarNotification;

    @Column(name = "code_variable")
    private String codeVariable;

    @Column(name = "description")
    private String description;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdVarNotification() {
        return this.idVarNotification;
    }

    public VariableNotification idVarNotification(String idVarNotification) {
        this.setIdVarNotification(idVarNotification);
        return this;
    }

    public void setIdVarNotification(String idVarNotification) {
        this.idVarNotification = idVarNotification;
    }

    public String getCodeVariable() {
        return this.codeVariable;
    }

    public VariableNotification codeVariable(String codeVariable) {
        this.setCodeVariable(codeVariable);
        return this;
    }

    public void setCodeVariable(String codeVariable) {
        this.codeVariable = codeVariable;
    }

    public String getDescription() {
        return this.description;
    }

    public VariableNotification description(String description) {
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
        return this.idVarNotification;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public VariableNotification setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableNotification)) {
            return false;
        }
        return getIdVarNotification() != null && getIdVarNotification().equals(((VariableNotification) o).getIdVarNotification());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariableNotification{" +
            "idVarNotification=" + getIdVarNotification() +
            ", codeVariable='" + getCodeVariable() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
