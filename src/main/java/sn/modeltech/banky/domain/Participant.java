package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;

/**
 * A Participant.
 */
@Entity
@Table(name = "participant")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Participant implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_participant")
    private String idParticipant;

    @Column(name = "code_participant")
    private String codeParticipant;

    @Column(name = "code_banque")
    private String codeBanque;

    @Column(name = "nom_banque")
    private String nomBanque;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "pays")
    private String pays;

    @Column(name = "is_actif")
    private Boolean isActif;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdParticipant() {
        return this.idParticipant;
    }

    public Participant idParticipant(String idParticipant) {
        this.setIdParticipant(idParticipant);
        return this;
    }

    public void setIdParticipant(String idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getCodeParticipant() {
        return this.codeParticipant;
    }

    public Participant codeParticipant(String codeParticipant) {
        this.setCodeParticipant(codeParticipant);
        return this;
    }

    public void setCodeParticipant(String codeParticipant) {
        this.codeParticipant = codeParticipant;
    }

    public String getCodeBanque() {
        return this.codeBanque;
    }

    public Participant codeBanque(String codeBanque) {
        this.setCodeBanque(codeBanque);
        return this;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public String getNomBanque() {
        return this.nomBanque;
    }

    public Participant nomBanque(String nomBanque) {
        this.setNomBanque(nomBanque);
        return this;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Participant libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPays() {
        return this.pays;
    }

    public Participant pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Participant isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idParticipant;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Participant setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant)) {
            return false;
        }
        return getIdParticipant() != null && getIdParticipant().equals(((Participant) o).getIdParticipant());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participant{" +
            "idParticipant=" + getIdParticipant() +
            ", codeParticipant='" + getCodeParticipant() + "'" +
            ", codeBanque='" + getCodeBanque() + "'" +
            ", nomBanque='" + getNomBanque() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", pays='" + getPays() + "'" +
            ", isActif='" + getIsActif() + "'" +
            "}";
    }
}
