package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.VariableNotification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VariableNotificationDTO implements Serializable {

    private String idVarNotification;

    private String codeVariable;

    private String description;

    public String getIdVarNotification() {
        return idVarNotification;
    }

    public void setIdVarNotification(String idVarNotification) {
        this.idVarNotification = idVarNotification;
    }

    public String getCodeVariable() {
        return codeVariable;
    }

    public void setCodeVariable(String codeVariable) {
        this.codeVariable = codeVariable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableNotificationDTO)) {
            return false;
        }

        VariableNotificationDTO variableNotificationDTO = (VariableNotificationDTO) o;
        if (this.idVarNotification == null) {
            return false;
        }
        return Objects.equals(this.idVarNotification, variableNotificationDTO.idVarNotification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idVarNotification);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariableNotificationDTO{" +
            "idVarNotification='" + getIdVarNotification() + "'" +
            ", codeVariable='" + getCodeVariable() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
