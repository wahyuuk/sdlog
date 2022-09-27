package co.id.ahm.sdlog.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "AHMSDLOG_HISSHIPQQS")
public class AhmsdlogHisshipqqs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NID", nullable = false)
    private Integer nid;

    @Column(name = "DSDSHPQQ_RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String documentNumber;

    @Column(name = "DSDSHPQQ_RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "DSDSHPQQ_MSDQQ_VSHIPTO")
    private String shipto;

    @Column(name = "DSDSHPQQ_MSDQQ_VMDCODE")
    private String shiptoMd;

    @Column(name = "VSTATACT")
    private String status;

    @Column(name = "VACTION")
    private String actionBy  = "ahm.dev01";;

    @Column(name = "DACTION")
    private Date actionDate;

    @Column(name = "DCREA", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "VCREA", nullable = false)
    private String createBy = "ahm.dev01";

    @Column(name = "DMODI")
    private Date updatedAt;

    @Column(name = "VMODI")
    private String updatedBy;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getMdCode() {
        return mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public String getShiptoMd() {
        return shiptoMd;
    }

    public void setShiptoMd(String shiptoMd) {
        this.shiptoMd = shiptoMd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
