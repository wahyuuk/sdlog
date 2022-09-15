package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AHMSDLOG_DTLMNGQQDOS")
public class AhmsdlogDtlMngQqDos extends DefaultEntity implements Serializable {

    @EmbeddedId
    private AhmsdlogDtlMngQqDosPk id;

    @Column(name = "NDOVINOLD")
    private Integer doVinOld;

    @Column(name = "NDOVINNEW")
    private Integer doVinNew;

    @Column(name = "DCREA", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private LocalDate updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public AhmsdlogDtlMngQqDosPk getId() {
        return id;
    }

    public void setId(AhmsdlogDtlMngQqDosPk id) {
        this.id = id;
    }

    public Integer getDoVinOld() {
        return doVinOld;
    }

    public void setDoVinOld(Integer doVinOld) {
        this.doVinOld = doVinOld;
    }

    public Integer getDoVinNew() {
        return doVinNew;
    }

    public void setDoVinNew(Integer doVinNew) {
        this.doVinNew = doVinNew;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
