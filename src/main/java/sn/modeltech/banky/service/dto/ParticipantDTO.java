package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Participant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParticipantDTO implements Serializable {

    private String idParticipant;

    private String codeParticipant;

    private String codeBanque;

    private String nomBanque;

    private String libelle;

    private String pays;

    private Boolean isActif;

    public String getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(String idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getCodeParticipant() {
        return codeParticipant;
    }

    public void setCodeParticipant(String codeParticipant) {
        this.codeParticipant = codeParticipant;
    }

    public String getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Boolean getIsActif() {
        return isActif;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParticipantDTO)) {
            return false;
        }

        ParticipantDTO participantDTO = (ParticipantDTO) o;
        if (this.idParticipant == null) {
            return false;
        }
        return Objects.equals(this.idParticipant, participantDTO.idParticipant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idParticipant);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParticipantDTO{" +
            "idParticipant='" + getIdParticipant() + "'" +
            ", codeParticipant='" + getCodeParticipant() + "'" +
            ", codeBanque='" + getCodeBanque() + "'" +
            ", nomBanque='" + getNomBanque() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", pays='" + getPays() + "'" +
            ", isActif='" + getIsActif() + "'" +
            "}";
    }
}
