package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A DispositifSercurite.
 */
@Entity
@Table(name = "dispositif_sercurite")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DispositifSercurite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_canal")
    private String idCanal;

    @Column(name = "id_type_transaction")
    private String idTypeTransaction;

    @Column(name = "id_dispositif")
    private String idDispositif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "dispositifSercurites" }, allowSetters = true)
    private Canal canal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "dispositifSercurites" }, allowSetters = true)
    private TypeTransaction typeTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "dispositifSercurites" }, allowSetters = true)
    private DispositifSignature dispositifSignature;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DispositifSercurite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCanal() {
        return this.idCanal;
    }

    public DispositifSercurite idCanal(String idCanal) {
        this.setIdCanal(idCanal);
        return this;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
    }

    public String getIdTypeTransaction() {
        return this.idTypeTransaction;
    }

    public DispositifSercurite idTypeTransaction(String idTypeTransaction) {
        this.setIdTypeTransaction(idTypeTransaction);
        return this;
    }

    public void setIdTypeTransaction(String idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public String getIdDispositif() {
        return this.idDispositif;
    }

    public DispositifSercurite idDispositif(String idDispositif) {
        this.setIdDispositif(idDispositif);
        return this;
    }

    public void setIdDispositif(String idDispositif) {
        this.idDispositif = idDispositif;
    }

    public Canal getCanal() {
        return this.canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public DispositifSercurite canal(Canal canal) {
        this.setCanal(canal);
        return this;
    }

    public TypeTransaction getTypeTransaction() {
        return this.typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public DispositifSercurite typeTransaction(TypeTransaction typeTransaction) {
        this.setTypeTransaction(typeTransaction);
        return this;
    }

    public DispositifSignature getDispositifSignature() {
        return this.dispositifSignature;
    }

    public void setDispositifSignature(DispositifSignature dispositifSignature) {
        this.dispositifSignature = dispositifSignature;
    }

    public DispositifSercurite dispositifSignature(DispositifSignature dispositifSignature) {
        this.setDispositifSignature(dispositifSignature);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositifSercurite)) {
            return false;
        }
        return getId() != null && getId().equals(((DispositifSercurite) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositifSercurite{" +
            "id=" + getId() +
            ", idCanal='" + getIdCanal() + "'" +
            ", idTypeTransaction='" + getIdTypeTransaction() + "'" +
            ", idDispositif='" + getIdDispositif() + "'" +
            "}";
    }
}
