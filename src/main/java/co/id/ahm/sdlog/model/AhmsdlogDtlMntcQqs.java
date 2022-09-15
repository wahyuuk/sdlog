package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AHMSDLOG_DTLMNTCQQS")
public class AhmsdlogDtlMntcQqs extends DefaultEntity implements Serializable {

    @EmbeddedId
    private AhmsdlogDtlMntcQqsPk id;

    @Column(name = "NRMNGH1OLD")
    private Integer qtyRemainHminVinOld;

    @Column(name = "NRMNGH1NEW")
    private Integer qtyRemainHminVinNew;

    @Column(name = "NDOPLNOLD")
    private Integer doVinOld;

    @Column(name = "NDOPLNNEW")
    private Integer doVinNew;

    @Column(name = "NRMNGOLD")
    private Integer qtyRemainHVinOld;

    @Column(name = "NRMNGNEW")
    private Integer qtyRemainHVinNew;

    @Column(name = "DCREA", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private LocalDate updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public AhmsdlogDtlMntcQqsPk getId() {
        return id;
    }

    public void setId(AhmsdlogDtlMntcQqsPk id) {
        this.id = id;
    }

    public Integer getQtyRemainHminVinOld() {
        return qtyRemainHminVinOld;
    }

    public void setQtyRemainHminVinOld(Integer qtyRemainHminVinOld) {
        this.qtyRemainHminVinOld = qtyRemainHminVinOld;
    }

    public Integer getQtyRemainHminVinNew() {
        return qtyRemainHminVinNew;
    }

    public void setQtyRemainHminVinNew(Integer qtyRemainHminVinNew) {
        this.qtyRemainHminVinNew = qtyRemainHminVinNew;
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

    public Integer getQtyRemainHVinOld() {
        return qtyRemainHVinOld;
    }

    public void setQtyRemainHVinOld(Integer qtyRemainHVinOld) {
        this.qtyRemainHVinOld = qtyRemainHVinOld;
    }

    public Integer getQtyRemainHVinNew() {
        return qtyRemainHVinNew;
    }

    public void setQtyRemainHVinNew(Integer qtyRemainHVinNew) {
        this.qtyRemainHVinNew = qtyRemainHVinNew;
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
