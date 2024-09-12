package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A ParametrageGlobal.
 */
@Entity
@Table(name = "parametrage_global")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParametrageGlobal implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_param_global")
    private String idParamGlobal;

    @Column(name = "code_param")
    private String codeParam;

    @Column(name = "type_param")
    private String typeParam;

    @Column(name = "valeur")
    private String valeur;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdParamGlobal() {
        return this.idParamGlobal;
    }

    public ParametrageGlobal idParamGlobal(String idParamGlobal) {
        this.setIdParamGlobal(idParamGlobal);
        return this;
    }

    public void setIdParamGlobal(String idParamGlobal) {
        this.idParamGlobal = idParamGlobal;
    }

    public String getCodeParam() {
        return this.codeParam;
    }

    public ParametrageGlobal codeParam(String codeParam) {
        this.setCodeParam(codeParam);
        return this;
    }

    public void setCodeParam(String codeParam) {
        this.codeParam = codeParam;
    }

    public String getTypeParam() {
        return this.typeParam;
    }

    public ParametrageGlobal typeParam(String typeParam) {
        this.setTypeParam(typeParam);
        return this;
    }

    public void setTypeParam(String typeParam) {
        this.typeParam = typeParam;
    }

    public String getValeur() {
        return this.valeur;
    }

    public ParametrageGlobal valeur(String valeur) {
        this.setValeur(valeur);
        return this;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idParamGlobal;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ParametrageGlobal setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametrageGlobal)) {
            return false;
        }
        return getIdParamGlobal() != null && getIdParamGlobal().equals(((ParametrageGlobal) o).getIdParamGlobal());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametrageGlobal{" +
            "idParamGlobal=" + getIdParamGlobal() +
            ", codeParam='" + getCodeParam() + "'" +
            ", typeParam='" + getTypeParam() + "'" +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
