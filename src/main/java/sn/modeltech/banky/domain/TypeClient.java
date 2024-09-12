package sn.modeltech.banky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A TypeClient.
 */
@Entity
@Table(name = "type_client")
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeClient implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_type_client")
    private String idTypeClient;

    @Column(name = "libelle")
    private String libelle;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeClient")
    @JsonIgnoreProperties(value = { "contrat", "typeClient" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdTypeClient() {
        return this.idTypeClient;
    }

    public TypeClient idTypeClient(String idTypeClient) {
        this.setIdTypeClient(idTypeClient);
        return this;
    }

    public void setIdTypeClient(String idTypeClient) {
        this.idTypeClient = idTypeClient;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeClient libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.idTypeClient;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public TypeClient setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setTypeClient(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setTypeClient(this));
        }
        this.clients = clients;
    }

    public TypeClient clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public TypeClient addClient(Client client) {
        this.clients.add(client);
        client.setTypeClient(this);
        return this;
    }

    public TypeClient removeClient(Client client) {
        this.clients.remove(client);
        client.setTypeClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeClient)) {
            return false;
        }
        return getIdTypeClient() != null && getIdTypeClient().equals(((TypeClient) o).getIdTypeClient());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeClient{" +
            "idTypeClient=" + getIdTypeClient() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
