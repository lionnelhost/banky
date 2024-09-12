package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.modeltech.banky.domain.enumeration.StatutAbonne;
import sn.modeltech.banky.domain.enumeration.TypePieceIdentite;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Abonne} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbonneDTO implements Serializable {

    private String idAbonne;

    private String indiceClient;

    private String nomAbonne;

    private String prenomAbonne;

    private String telephone;

    private String email;

    private TypePieceIdentite typePieceIdentite;

    private String numPieceIdentite;

    private StatutAbonne statut;

    private String identifiant;

    public String getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(String idAbonne) {
        this.idAbonne = idAbonne;
    }

    public String getIndiceClient() {
        return indiceClient;
    }

    public void setIndiceClient(String indiceClient) {
        this.indiceClient = indiceClient;
    }

    public String getNomAbonne() {
        return nomAbonne;
    }

    public void setNomAbonne(String nomAbonne) {
        this.nomAbonne = nomAbonne;
    }

    public String getPrenomAbonne() {
        return prenomAbonne;
    }

    public void setPrenomAbonne(String prenomAbonne) {
        this.prenomAbonne = prenomAbonne;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypePieceIdentite getTypePieceIdentite() {
        return typePieceIdentite;
    }

    public void setTypePieceIdentite(TypePieceIdentite typePieceIdentite) {
        this.typePieceIdentite = typePieceIdentite;
    }

    public String getNumPieceIdentite() {
        return numPieceIdentite;
    }

    public void setNumPieceIdentite(String numPieceIdentite) {
        this.numPieceIdentite = numPieceIdentite;
    }

    public StatutAbonne getStatut() {
        return statut;
    }

    public void setStatut(StatutAbonne statut) {
        this.statut = statut;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbonneDTO)) {
            return false;
        }

        AbonneDTO abonneDTO = (AbonneDTO) o;
        if (this.idAbonne == null) {
            return false;
        }
        return Objects.equals(this.idAbonne, abonneDTO.idAbonne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idAbonne);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbonneDTO{" +
            "idAbonne='" + getIdAbonne() + "'" +
            ", indiceClient='" + getIndiceClient() + "'" +
            ", nomAbonne='" + getNomAbonne() + "'" +
            ", prenomAbonne='" + getPrenomAbonne() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", typePieceIdentite='" + getTypePieceIdentite() + "'" +
            ", numPieceIdentite='" + getNumPieceIdentite() + "'" +
            ", statut='" + getStatut() + "'" +
            ", identifiant='" + getIdentifiant() + "'" +
            "}";
    }
}
