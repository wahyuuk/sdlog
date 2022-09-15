package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AHMSDLOG_HDRSHIPQQS")
public class AhmsdlogHdrShipQqs extends DefaultEntity implements Serializable {

    @EmbeddedId
    private AhmsdlogHdrShipQqsPk id;

    @Column(name = "NSUMDP")
    private Integer dpSumQty;

    @Column(name = "NDEVIATION")
    private Integer deviation;

    @Column(name = "NMONTH")
    private Integer month;

    @Column(name = "NYEAR")
    private Integer year;

    @Column(name = "NDPCQTY")
    private Integer dpColorQty;

    @Column(name = "DCREA", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private LocalDate updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public AhmsdlogHdrShipQqsPk getId() {
        return id;
    }

    public void setId(AhmsdlogHdrShipQqsPk id) {
        this.id = id;
    }

    public Integer getDpSumQty() {
        return dpSumQty;
    }

    public void setDpSumQty(Integer dpSumQty) {
        this.dpSumQty = dpSumQty;
    }

    public Integer getDeviation() {
        return deviation;
    }

    public void setDeviation(Integer deviation) {
        this.deviation = deviation;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDpColorQty() {
        return dpColorQty;
    }

    public void setDpColorQty(Integer dpColorQty) {
        this.dpColorQty = dpColorQty;
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
