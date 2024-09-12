package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.modeltech.banky.domain.enumeration.Devise;
import sn.modeltech.banky.domain.enumeration.Langue;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Banque} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanqueDTO implements Serializable {

    private String idBanque;

    private String code;

    private Devise devise;

    private Langue langue;

    private String logo;

    private String codeSwift;

    private String codeIban;

    private String codePays;

    private String fuseauHoraire;

    private String cutOffTime;

    private String codeParticipant;

    private String libelleParticipant;

    public String getIdBanque() {
        return idBanque;
    }

    public void setIdBanque(String idBanque) {
        this.idBanque = idBanque;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCodeSwift() {
        return codeSwift;
    }

    public void setCodeSwift(String codeSwift) {
        this.codeSwift = codeSwift;
    }

    public String getCodeIban() {
        return codeIban;
    }

    public void setCodeIban(String codeIban) {
        this.codeIban = codeIban;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getFuseauHoraire() {
        return fuseauHoraire;
    }

    public void setFuseauHoraire(String fuseauHoraire) {
        this.fuseauHoraire = fuseauHoraire;
    }

    public String getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(String cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getCodeParticipant() {
        return codeParticipant;
    }

    public void setCodeParticipant(String codeParticipant) {
        this.codeParticipant = codeParticipant;
    }

    public String getLibelleParticipant() {
        return libelleParticipant;
    }

    public void setLibelleParticipant(String libelleParticipant) {
        this.libelleParticipant = libelleParticipant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BanqueDTO)) {
            return false;
        }

        BanqueDTO banqueDTO = (BanqueDTO) o;
        if (this.idBanque == null) {
            return false;
        }
        return Objects.equals(this.idBanque, banqueDTO.idBanque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idBanque);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanqueDTO{" +
            "idBanque='" + getIdBanque() + "'" +
            ", code='" + getCode() + "'" +
            ", devise='" + getDevise() + "'" +
            ", langue='" + getLangue() + "'" +
            ", logo='" + getLogo() + "'" +
            ", codeSwift='" + getCodeSwift() + "'" +
            ", codeIban='" + getCodeIban() + "'" +
            ", codePays='" + getCodePays() + "'" +
            ", fuseauHoraire='" + getFuseauHoraire() + "'" +
            ", cutOffTime='" + getCutOffTime() + "'" +
            ", codeParticipant='" + getCodeParticipant() + "'" +
            ", libelleParticipant='" + getLibelleParticipant() + "'" +
            "}";
    }
}
