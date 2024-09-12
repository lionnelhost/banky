package sn.modeltech.banky.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sn.modeltech.banky.domain.DispositifSercurite} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DispositifSercuriteDTO implements Serializable {

    private Long id;

    private String idCanal;

    private String idTypeTransaction;

    private String idDispositif;

    private CanalDTO canal;

    private TypeTransactionDTO typeTransaction;

    private DispositifSignatureDTO dispositifSignature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
    }

    public String getIdTypeTransaction() {
        return idTypeTransaction;
    }

    public void setIdTypeTransaction(String idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public String getIdDispositif() {
        return idDispositif;
    }

    public void setIdDispositif(String idDispositif) {
        this.idDispositif = idDispositif;
    }

    public CanalDTO getCanal() {
        return canal;
    }

    public void setCanal(CanalDTO canal) {
        this.canal = canal;
    }

    public TypeTransactionDTO getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransactionDTO typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public DispositifSignatureDTO getDispositifSignature() {
        return dispositifSignature;
    }

    public void setDispositifSignature(DispositifSignatureDTO dispositifSignature) {
        this.dispositifSignature = dispositifSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositifSercuriteDTO)) {
            return false;
        }

        DispositifSercuriteDTO dispositifSercuriteDTO = (DispositifSercuriteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dispositifSercuriteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositifSercuriteDTO{" +
            "id=" + getId() +
            ", idCanal='" + getIdCanal() + "'" +
            ", idTypeTransaction='" + getIdTypeTransaction() + "'" +
            ", idDispositif='" + getIdDispositif() + "'" +
            ", canal=" + getCanal() +
            ", typeTransaction=" + getTypeTransaction() +
            ", dispositifSignature=" + getDispositifSignature() +
            "}";
    }
}
