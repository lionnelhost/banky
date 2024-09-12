package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;
import sn.modeltech.banky.domain.enumeration.Devise;
import sn.modeltech.banky.domain.enumeration.Langue;

/**
 * A Banque.
 */
@Entity
@Table(name = "banque")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Banque implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_banque")
    private String idBanque;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "devise")
    private Devise devise;

    @Enumerated(EnumType.STRING)
    @Column(name = "langue")
    private Langue langue;

    @Column(name = "logo")
    private String logo;

    @Column(name = "code_swift")
    private String codeSwift;

    @Column(name = "code_iban")
    private String codeIban;

    @Column(name = "code_pays")
    private String codePays;

    @Column(name = "fuseau_horaire")
    private String fuseauHoraire;

    @Column(name = "cut_off_time")
    private String cutOffTime;

    @Column(name = "code_participant")
    private String codeParticipant;

    @Column(name = "libelle_participant")
    private String libelleParticipant;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banque")
    @JsonIgnoreProperties(value = { "banque" }, allowSetters = true)
    private Set<Agence> agences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdBanque() {
        return this.idBanque;
    }

    public Banque idBanque(String idBanque) {
        this.setIdBanque(idBanque);
        return this;
    }

    public void setIdBanque(String idBanque) {
        this.idBanque = idBanque;
    }

    public String getCode() {
        return this.code;
    }

    public Banque code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Devise getDevise() {
        return this.devise;
    }

    public Banque devise(Devise devise) {
        this.setDevise(devise);
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Langue getLangue() {
        return this.langue;
    }

    public Banque langue(Langue langue) {
        this.setLangue(langue);
        return this;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public String getLogo() {
        return this.logo;
    }

    public Banque logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCodeSwift() {
        return this.codeSwift;
    }

    public Banque codeSwift(String codeSwift) {
        this.setCodeSwift(codeSwift);
        return this;
    }

    public void setCodeSwift(String codeSwift) {
        this.codeSwift = codeSwift;
    }

    public String getCodeIban() {
        return this.codeIban;
    }

    public Banque codeIban(String codeIban) {
        this.setCodeIban(codeIban);
        return this;
    }

    public void setCodeIban(String codeIban) {
        this.codeIban = codeIban;
    }

    public String getCodePays() {
        return this.codePays;
    }

    public Banque codePays(String codePays) {
        this.setCodePays(codePays);
        return this;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getFuseauHoraire() {
        return this.fuseauHoraire;
    }

    public Banque fuseauHoraire(String fuseauHoraire) {
        this.setFuseauHoraire(fuseauHoraire);
        return this;
    }

    public void setFuseauHoraire(String fuseauHoraire) {
        this.fuseauHoraire = fuseauHoraire;
    }

    public String getCutOffTime() {
        return this.cutOffTime;
    }

    public Banque cutOffTime(String cutOffTime) {
        this.setCutOffTime(cutOffTime);
        return this;
    }

    public void setCutOffTime(String cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getCodeParticipant() {
        return this.codeParticipant;
    }

    public Banque codeParticipant(String codeParticipant) {
        this.setCodeParticipant(codeParticipant);
        return this;
    }

    public void setCodeParticipant(String codeParticipant) {
        this.codeParticipant = codeParticipant;
    }

    public String getLibelleParticipant() {
        return this.libelleParticipant;
    }

    public Banque libelleParticipant(String libelleParticipant) {
        this.setLibelleParticipant(libelleParticipant);
        return this;
    }

    public void setLibelleParticipant(String libelleParticipant) {
        this.libelleParticipant = libelleParticipant;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idBanque;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Banque setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Agence> getAgences() {
        return this.agences;
    }

    public void setAgences(Set<Agence> agences) {
        if (this.agences != null) {
            this.agences.forEach(i -> i.setBanque(null));
        }
        if (agences != null) {
            agences.forEach(i -> i.setBanque(this));
        }
        this.agences = agences;
    }

    public Banque agences(Set<Agence> agences) {
        this.setAgences(agences);
        return this;
    }

    public Banque addAgence(Agence agence) {
        this.agences.add(agence);
        agence.setBanque(this);
        return this;
    }

    public Banque removeAgence(Agence agence) {
        this.agences.remove(agence);
        agence.setBanque(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banque)) {
            return false;
        }
        return getIdBanque() != null && getIdBanque().equals(((Banque) o).getIdBanque());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banque{" +
            "idBanque=" + getIdBanque() +
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
