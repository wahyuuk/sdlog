package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AHMSDLOG_TXNDPCOLORS")
public class AhmsdlogTxnDpColor implements Serializable {

    @EmbeddedId
    private AhmsdlogTxnDpColorPk id;

    @Column(name = "VUOMID", nullable = false)
    private String vuomid;

    @Column(name = "NDPCQTY", nullable = false)
    private Integer dpColorQty;

    @Column(name = "NPOQTY", nullable = false)
    private Integer poQty;

    @Column(name = "DPOPULL", nullable = false)
    private LocalDate poDate;

    @Column(name = "DCREA", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private LocalDate updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public AhmsdlogTxnDpColorPk getId() {
        return id;
    }

    public void setId(AhmsdlogTxnDpColorPk id) {
        this.id = id;
    }

    public String getVuomid() {
        return vuomid;
    }

    public void setVuomid(String vuomid) {
        this.vuomid = vuomid;
    }

    public Integer getDpColorQty() {
        return dpColorQty;
    }

    public void setDpColorQty(Integer dpColorQty) {
        this.dpColorQty = dpColorQty;
    }

    public Integer getPoQty() {
        return poQty;
    }

    public void setPoQty(Integer poQty) {
        this.poQty = poQty;
    }

    public LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(LocalDate poDate) {
        this.poDate = poDate;
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
