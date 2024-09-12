package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;
import sn.modeltech.banky.domain.enumeration.StatutCompteBancaire;

/**
 * A CompteBancaire.
 */
@Entity
@Table(name = "compte_bancaire")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteBancaire implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_compte_bancaire")
    private String idCompteBancaire;

    @Column(name = "age")
    private String age;

    @Column(name = "ncp")
    private String ncp;

    @Column(name = "sde")
    private String sde;

    @Column(name = "sin")
    private String sin;

    @Column(name = "solde_disponible")
    private String soldeDisponible;

    @Column(name = "rib")
    private String rib;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatutCompteBancaire status;

    @Transient
    private boolean isPersisted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "typeContrat", "client", "compteBancaires", "contratAbonnements", "contratAbonnementComptes" },
        allowSetters = true
    )
    private Contrat contrat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compteBancaire")
    @JsonIgnoreProperties(value = { "contrat", "abonne", "compteBancaire" }, allowSetters = true)
    private Set<ContratAbonnementCompte> contratAbonnementComptes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdCompteBancaire() {
        return this.idCompteBancaire;
    }

    public CompteBancaire idCompteBancaire(String idCompteBancaire) {
        this.setIdCompteBancaire(idCompteBancaire);
        return this;
    }

    public void setIdCompteBancaire(String idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public String getAge() {
        return this.age;
    }

    public CompteBancaire age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNcp() {
        return this.ncp;
    }

    public CompteBancaire ncp(String ncp) {
        this.setNcp(ncp);
        return this;
    }

    public void setNcp(String ncp) {
        this.ncp = ncp;
    }

    public String getSde() {
        return this.sde;
    }

    public CompteBancaire sde(String sde) {
        this.setSde(sde);
        return this;
    }

    public void setSde(String sde) {
        this.sde = sde;
    }

    public String getSin() {
        return this.sin;
    }

    public CompteBancaire sin(String sin) {
        this.setSin(sin);
        return this;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getSoldeDisponible() {
        return this.soldeDisponible;
    }

    public CompteBancaire soldeDisponible(String soldeDisponible) {
        this.setSoldeDisponible(soldeDisponible);
        return this;
    }

    public void setSoldeDisponible(String soldeDisponible) {
        this.soldeDisponible = soldeDisponible;
    }

    public String getRib() {
        return this.rib;
    }

    public CompteBancaire rib(String rib) {
        this.setRib(rib);
        return this;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public StatutCompteBancaire getStatus() {
        return this.status;
    }

    public CompteBancaire status(StatutCompteBancaire status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatutCompteBancaire status) {
        this.status = status;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idCompteBancaire;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public CompteBancaire setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Contrat getContrat() {
        return this.contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public CompteBancaire contrat(Contrat contrat) {
        this.setContrat(contrat);
        return this;
    }

    public Set<ContratAbonnementCompte> getContratAbonnementComptes() {
        return this.contratAbonnementComptes;
    }

    public void setContratAbonnementComptes(Set<ContratAbonnementCompte> contratAbonnementComptes) {
        if (this.contratAbonnementComptes != null) {
            this.contratAbonnementComptes.forEach(i -> i.setCompteBancaire(null));
        }
        if (contratAbonnementComptes != null) {
            contratAbonnementComptes.forEach(i -> i.setCompteBancaire(this));
        }
        this.contratAbonnementComptes = contratAbonnementComptes;
    }

    public CompteBancaire contratAbonnementComptes(Set<ContratAbonnementCompte> contratAbonnementComptes) {
        this.setContratAbonnementComptes(contratAbonnementComptes);
        return this;
    }

    public CompteBancaire addContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        this.contratAbonnementComptes.add(contratAbonnementCompte);
        contratAbonnementCompte.setCompteBancaire(this);
        return this;
    }

    public CompteBancaire removeContratAbonnementCompte(ContratAbonnementCompte contratAbonnementCompte) {
        this.contratAbonnementComptes.remove(contratAbonnementCompte);
        contratAbonnementCompte.setCompteBancaire(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteBancaire)) {
            return false;
        }
        return getIdCompteBancaire() != null && getIdCompteBancaire().equals(((CompteBancaire) o).getIdCompteBancaire());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteBancaire{" +
            "idCompteBancaire=" + getIdCompteBancaire() +
            ", age='" + getAge() + "'" +
            ", ncp='" + getNcp() + "'" +
            ", sde='" + getSde() + "'" +
            ", sin='" + getSin() + "'" +
            ", soldeDisponible='" + getSoldeDisponible() + "'" +
            ", rib='" + getRib() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
