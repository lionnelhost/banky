package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.MessageErreur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageErreurDTO implements Serializable {

    private String idMessageErreur;

    private String codeErreur;

    private String description;

    public String getIdMessageErreur() {
        return idMessageErreur;
    }

    public void setIdMessageErreur(String idMessageErreur) {
        this.idMessageErreur = idMessageErreur;
    }

    public String getCodeErreur() {
        return codeErreur;
    }

    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
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
        if (!(o instanceof MessageErreurDTO)) {
            return false;
        }

        MessageErreurDTO messageErreurDTO = (MessageErreurDTO) o;
        if (this.idMessageErreur == null) {
            return false;
        }
        return Objects.equals(this.idMessageErreur, messageErreurDTO.idMessageErreur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idMessageErreur);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageErreurDTO{" +
            "idMessageErreur='" + getIdMessageErreur() + "'" +
            ", codeErreur='" + getCodeErreur() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
