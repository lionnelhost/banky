package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.ParametrageGlobal} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParametrageGlobalDTO implements Serializable {

    private String idParamGlobal;

    private String codeParam;

    private String typeParam;

    private String valeur;

    public String getIdParamGlobal() {
        return idParamGlobal;
    }

    public void setIdParamGlobal(String idParamGlobal) {
        this.idParamGlobal = idParamGlobal;
    }

    public String getCodeParam() {
        return codeParam;
    }

    public void setCodeParam(String codeParam) {
        this.codeParam = codeParam;
    }

    public String getTypeParam() {
        return typeParam;
    }

    public void setTypeParam(String typeParam) {
        this.typeParam = typeParam;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametrageGlobalDTO)) {
            return false;
        }

        ParametrageGlobalDTO parametrageGlobalDTO = (ParametrageGlobalDTO) o;
        if (this.idParamGlobal == null) {
            return false;
        }
        return Objects.equals(this.idParamGlobal, parametrageGlobalDTO.idParamGlobal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idParamGlobal);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametrageGlobalDTO{" +
            "idParamGlobal='" + getIdParamGlobal() + "'" +
            ", codeParam='" + getCodeParam() + "'" +
            ", typeParam='" + getTypeParam() + "'" +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
