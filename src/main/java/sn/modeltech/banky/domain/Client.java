package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_client")
    private String idClient;

    @Column(name = "indice_client")
    private String indiceClient;

    @Column(name = "nom_client")
    private String nomClient;

    @Column(name = "prenom_client")
    private String prenomClient;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Transient
    private boolean isPersisted;

    @JsonIgnoreProperties(
        value = { "typeContrat", "client", "compteBancaires", "contratAbonnements", "contratAbonnementComptes" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Contrat contrat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "clients" }, allowSetters = true)
    private TypeClient typeClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdClient() {
        return this.idClient;
    }

    public Client idClient(String idClient) {
        this.setIdClient(idClient);
        return this;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIndiceClient() {
        return this.indiceClient;
    }

    public Client indiceClient(String indiceClient) {
        this.setIndiceClient(indiceClient);
        return this;
    }

    public void setIndiceClient(String indiceClient) {
        this.indiceClient = indiceClient;
    }

    public String getNomClient() {
        return this.nomClient;
    }

    public Client nomClient(String nomClient) {
        this.setNomClient(nomClient);
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return this.prenomClient;
    }

    public Client prenomClient(String prenomClient) {
        this.setPrenomClient(prenomClient);
        return this;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public Client raisonSociale(String raisonSociale) {
        this.setRaisonSociale(raisonSociale);
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Client telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idClient;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Client setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Contrat getContrat() {
        return this.contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Client contrat(Contrat contrat) {
        this.setContrat(contrat);
        return this;
    }

    public TypeClient getTypeClient() {
        return this.typeClient;
    }

    public void setTypeClient(TypeClient typeClient) {
        this.typeClient = typeClient;
    }

    public Client typeClient(TypeClient typeClient) {
        this.setTypeClient(typeClient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return getIdClient() != null && getIdClient().equals(((Client) o).getIdClient());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "idClient=" + getIdClient() +
            ", indiceClient='" + getIndiceClient() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            ", prenomClient='" + getPrenomClient() + "'" +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
