package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AHMSDLOG_HDRMNTCQQS")
public class AhmsdlogHdrMntcQqs extends DefaultEntity implements Serializable {

    @EmbeddedId
    private AhmsdlogHdrMntcQqsPk id;

    @Column(name = "NREFVINOLD")
    private Integer qtyReffMpsVinOld;

    @Column(name = "NREFVINNEW")
    private Integer qtyReffMpsVinNew;

    @Column(name = "NDPVINOLD")
    private Integer distPlanVinOld;

    @Column(name = "NDPVINNEW")
    private Integer distPlanVinNew;

    @Column(name = "NDOSUM")
    private Integer planDoOpen;

    @Column(name = "VREMARK")
    private String remark;

    @Column(name = "NDIFDPDO")
    private Integer qtyDifferent;

    @Column(name = "NDOACTL")
    private Integer actualDo;

    @Column(name = "DCREA", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private LocalDate updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public AhmsdlogHdrMntcQqsPk getId() {
        return id;
    }

    public void setId(AhmsdlogHdrMntcQqsPk id) {
        this.id = id;
    }

    public Integer getQtyReffMpsVinOld() {
        return qtyReffMpsVinOld;
    }

    public void setQtyReffMpsVinOld(Integer qtyReffMpsVinOld) {
        this.qtyReffMpsVinOld = qtyReffMpsVinOld;
    }

    public Integer getQtyReffMpsVinNew() {
        return qtyReffMpsVinNew;
    }

    public void setQtyReffMpsVinNew(Integer qtyReffMpsVinNew) {
        this.qtyReffMpsVinNew = qtyReffMpsVinNew;
    }

    public Integer getDistPlanVinOld() {
        return distPlanVinOld;
    }

    public void setDistPlanVinOld(Integer distPlanVinOld) {
        this.distPlanVinOld = distPlanVinOld;
    }

    public Integer getDistPlanVinNew() {
        return distPlanVinNew;
    }

    public void setDistPlanVinNew(Integer distPlanVinNew) {
        this.distPlanVinNew = distPlanVinNew;
    }

    public Integer getPlanDoOpen() {
        return planDoOpen;
    }

    public void setPlanDoOpen(Integer planDoOpen) {
        this.planDoOpen = planDoOpen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getQtyDifferent() {
        return qtyDifferent;
    }

    public void setQtyDifferent(Integer qtyDifferent) {
        this.qtyDifferent = qtyDifferent;
    }

    public Integer getActualDo() {
        return actualDo;
    }

    public void setActualDo(Integer actualDo) {
        this.actualDo = actualDo;
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
