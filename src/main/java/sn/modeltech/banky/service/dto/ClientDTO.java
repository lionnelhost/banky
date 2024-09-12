package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.Client} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientDTO implements Serializable {

    private String idClient;

    private String indiceClient;

    private String nomClient;

    private String prenomClient;

    private String raisonSociale;

    private String telephone;

    private String email;

    private ContratDTO contrat;

    private TypeClientDTO typeClient;

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIndiceClient() {
        return indiceClient;
    }

    public void setIndiceClient(String indiceClient) {
        this.indiceClient = indiceClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
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

    public ContratDTO getContrat() {
        return contrat;
    }

    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }

    public TypeClientDTO getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(TypeClientDTO typeClient) {
        this.typeClient = typeClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.idClient == null) {
            return false;
        }
        return Objects.equals(this.idClient, clientDTO.idClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idClient);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "idClient='" + getIdClient() + "'" +
            ", indiceClient='" + getIndiceClient() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            ", prenomClient='" + getPrenomClient() + "'" +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", contrat=" + getContrat() +
            ", typeClient=" + getTypeClient() +
            "}";
    }
}
