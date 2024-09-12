package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.ParametrageNotification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParametrageNotificationDTO implements Serializable {

    private String idParamNotif;

    private String objetNotif;

    private String typeNotif;

    private String contenu;

    public String getIdParamNotif() {
        return idParamNotif;
    }

    public void setIdParamNotif(String idParamNotif) {
        this.idParamNotif = idParamNotif;
    }

    public String getObjetNotif() {
        return objetNotif;
    }

    public void setObjetNotif(String objetNotif) {
        this.objetNotif = objetNotif;
    }

    public String getTypeNotif() {
        return typeNotif;
    }

    public void setTypeNotif(String typeNotif) {
        this.typeNotif = typeNotif;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametrageNotificationDTO)) {
            return false;
        }

        ParametrageNotificationDTO parametrageNotificationDTO = (ParametrageNotificationDTO) o;
        if (this.idParamNotif == null) {
            return false;
        }
        return Objects.equals(this.idParamNotif, parametrageNotificationDTO.idParamNotif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idParamNotif);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametrageNotificationDTO{" +
            "idParamNotif='" + getIdParamNotif() + "'" +
            ", objetNotif='" + getObjetNotif() + "'" +
            ", typeNotif='" + getTypeNotif() + "'" +
            ", contenu='" + getContenu() + "'" +
            "}";
    }
}
