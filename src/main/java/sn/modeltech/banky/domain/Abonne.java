package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;
import sn.modeltech.banky.domain.enumeration.StatutAbonne;
import sn.modeltech.banky.domain.enumeration.TypePieceIdentite;

/**
 * A Abonne.
 */
@Entity
@Table(name = "abonne")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Abonne implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_abonne")
    private String idAbonne;

    @Column(name = "indice_client")
    private String indiceClient;

    @Column(name = "nom_abonne")
    private String nomAbonne;

    @Column(name = "prenom_abonne")
    private String prenomAbonne;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_piece_identite")
    private TypePieceIdentite typePieceIdentite;

    @Column(name = "num_piece_identite")
    private String numPieceIdentite;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutAbonne statut;

    @Column(name = "identifiant")
    private String identifiant;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "abonne")
    @JsonIgnoreProperties(value = { "contrat", "abonne" }, allowSetters = true)
    private Set<ContratAbonnement> contratAbonnements = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "abonne")
    @JsonIgnoreProperties(value = { "contrat", "abonne", "compteBancaire" }, allowSetters = true)
    private Set<ContratAbonnementCompte> contratAbonnementComptes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdAbonne() {
        return this.idAbonne;
    }

    public Abonne idAbonne(String idAbonne) {
        this.setIdAbonne(idAbonne);
        return this;
    }

    public void setIdAbonne(String idAbonne) {
        this.idAbonne = idAbonne;
    }

    public String getIndiceClient() {
        return this.indiceClient;
    }

    public Abonne indiceClient(String indiceClient) {
        this.setIndiceClient(indiceClient);
        return this;
    }

    public void setIndiceClient(String indiceClient) {
        this.indiceClient = indiceClient;
    }

    public String getNomAbonne() {
        return this.nomAbonne;
    }

    public Abonne nomAbonne(String nomAbonne) {
        this.setNomAbonne(nomAbonne);
        return this;
    }

    public void setNomAbonne(String nomAbonne) {
        this.nomAbonne = nomAbonne;
    }

    public String getPrenomAbonne() {
        return this.prenomAbonne;
    }

    public Abonne prenomAbonne(String prenomAbonne) {
        this.setPrenomAbonne(prenomAbonne);
        return this;
    }

    public void setPrenomAbonne(String prenomAbonne) {
        this.prenomAbonne = prenomAbonne;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Abonne telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Abonne email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypePieceIdentite getTypePieceIdentite() {
        return this.typePieceIdentite;
    }

    public Abonne typePieceIdentite(TypePieceIdentite typePieceIdentite) {
        this.setTypePieceIdentite(typePieceIdentite);
        return this;
    }

    public void setTypePieceIdentite(TypePieceIdentite typePieceIdentite) {
        this.typePieceIdentite = typePieceIdentite;
    }

    public String getNumPieceIdentite() {
        return this.numPieceIdentite;
    }

    public Abonne numPieceIdentite(String numPieceIdentite) {
        this.setNumPieceIdentite(numPieceIdentite);
        return this;
    }

    public void setNumPieceIdentite(String numPieceIdentite) {
        this.numPieceIdentite = numPieceIdentite;
    }

    public StatutAbonne getStatut() {
        return this.statut;
    }

    public Abonne statut(StatutAbonne statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutAbonne statut) {
        this.statut = statut;
    }

    public String getIdentifiant() {
        return this.identifiant;
    }

    public Abonne identifiant(String identifiant) {
        this.setIdentifiant(identifiant);
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idAbonne;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Abonne setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<ContratAbonnement> getContratAbonnements() {
        return this.contratAbonnements;
    }

    public void setContratAbonnements(Set<ContratAbonnement> contratAbonnements) {
        if (this.contratAbonnements != null) {
            this.contratAbonnements.forEach(i -> i.setAbonne(null));
        }
        if (contratAbonnements != null) {
            contratAbonnements.forEach(i -> i.setAbonne(this));
        }
        this.contratAbonnements = contratAbonnements;
    }

    public Abonne contratAbonnements(Set<ContratAbonnement> contratAbonnements) {
        this.setContratAbonnements(contratAbonnements);
        return this;
    }

    public Abonne addContratAbonnement(ContratAbonnement contratAbonnement) {
        this.contratAbonnements.add(contratAbonnement);
        contratAbonnement.setAbonne(this);
        return this;
    }

    public Abonne removeContratAbonnement(ContratAbonnement contratAbonnement) {
        this.contratAbonnements.remove(contratAbonnement);
        contratAbonnement.setAbonne(null);
        return this;
    }

    public Set<ContratAbonnementCompte> getContratAbonnementComptes() {
        return this.contratAbonnementComptes;
    }

    public void setContratAbonnementComptes(Set<ContratAbonnementCompte> contratAbonnementComptes) {
        if (this.contratAbonnementComptes != null) {
            this.contratAbonnementComptes.forEach(i -> i.setAbonne(null));
        }
        if (contratAbonnementComptes != null) {
            contratAbonnementComptes.forEach(i -> i.setAbonne(this));
        }
        this.contratAbonnementComptes = contratAbonnementComptes;
    }

    public Abonne contratAbonnementComptes(Set<ContratAbonnementCompte> contratAbonnementComptes) {
        this.setContratAbonnementComptes(contratAbonnementComptes);
        return this;
    }

    public Abonne addContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        this.contratAbonnementComptes.add(contratAbonnementCompte);
        contratAbonnementCompte.setAbonne(this);
        return this;
    }

    public Abonne removeContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        this.contratAbonnementComptes.remove(contratAbonnementCompte);
        contratAbonnementCompte.setAbonne(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Abonne)) {
            return false;
        }
        return getIdAbonne() != null && getIdAbonne().equals(((Abonne) o).getIdAbonne());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Abonne{" +
            "idAbonne=" + getIdAbonne() +
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
