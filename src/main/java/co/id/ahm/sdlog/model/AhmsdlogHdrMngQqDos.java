package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AHMSDLOG_HDRMNGQQDOS")
public class AhmsdlogHdrMngQqDos extends DefaultEntity implements Serializable {

    @EmbeddedId
    private AhmsdlogHdrMngQqDosPk id;

    @Column(name = "NDPVINOLD")
    private Integer distPlanQtyVinOld;

    @Column(name = "NDPVINNEW")
    private Integer distPlanQtyVinNew;

    @Column(name = "NDOVINOLD")
    private Integer doQtyVinOld;

    @Column(name = "NDOVINNEW")
    private Integer doQtyVinNew;

    @Column(name = "NQTYVINNEW")
    private Integer nQtyVinNew;

    @Column(name = "NQTYVINOLD")
    private Integer nQtyVinOld;

    @Column(name = "NDFRNCDPDO")
    private Integer differentQty;

    @Column(name = "DCREA", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private LocalDate updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public AhmsdlogHdrMngQqDosPk getId() {
        return id;
    }

    public void setId(AhmsdlogHdrMngQqDosPk id) {
        this.id = id;
    }

    public Integer getDistPlanQtyVinOld() {
        return distPlanQtyVinOld;
    }

    public void setDistPlanQtyVinOld(Integer distPlanQtyVinOld) {
        this.distPlanQtyVinOld = distPlanQtyVinOld;
    }

    public Integer getDistPlanQtyVinNew() {
        return distPlanQtyVinNew;
    }

    public void setDistPlanQtyVinNew(Integer distPlanQtyVinNew) {
        this.distPlanQtyVinNew = distPlanQtyVinNew;
    }

    public Integer getDoQtyVinOld() {
        return doQtyVinOld;
    }

    public void setDoQtyVinOld(Integer doQtyVinOld) {
        this.doQtyVinOld = doQtyVinOld;
    }

    public Integer getDoQtyVinNew() {
        return doQtyVinNew;
    }

    public void setDoQtyVinNew(Integer doQtyVinNew) {
        this.doQtyVinNew = doQtyVinNew;
    }

    public Integer getDifferentQty() {
        return differentQty;
    }

    public void setDifferentQty(Integer differentQty) {
        this.differentQty = differentQty;
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

    public Integer getnQtyVinNew() {
        return nQtyVinNew;
    }

    public void setnQtyVinNew(Integer nQtyVinNew) {
        this.nQtyVinNew = nQtyVinNew;
    }

    public Integer getnQtyVinOld() {
        return nQtyVinOld;
    }

    public void setnQtyVinOld(Integer nQtyVinOld) {
        this.nQtyVinOld = nQtyVinOld;
    }
}
