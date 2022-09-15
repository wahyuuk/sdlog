package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

@Embeddable
public class AhmsdlogDtlMntcQqsPk implements Serializable {

    @Column(name = "DSDMNTQQ_DSDSHPQQ_RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumber;

    @Column(name = "DSDMNTQQ_DSDSHPQQ_RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "DSDMNTQQ_RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumberHeader;

    @Column(name = "DSDMNTQQ_RSDMNGQQ_RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCodeHeader;

    @Column(name = "DSDMNTQQ_DSDSHPQQ_MSDQQ_VSHIPTO", nullable = false)
    private String shipto;

    @Column(name = "DSDMNTQQ_DSDSHPQQ_MSDQQ_VMDCODE", nullable = false)
    private String shiptoMdCode;

    @Column(name = "DSDMNTQQ_RSDMNGQQ_MSDUNTGP_NID", nullable = false)
    private Integer unitGroupId;

    @Column(name = "DSDMNTQQ_RSDMNGQQ_DSDMCT_RSDMCT_VMCTYPEID", nullable = false)
    private String mcTypeId;

    @Column(name = "DSDMNTQQ_RSDMNGQQ_DSDMCT_VCOLORID", nullable = false)
    private String colorId;

    @Column(name = "DMNTN", nullable = false)
    private LocalDate dateMaintain;

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getMdCode() {
        return mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getDocNumberHeader() {
        return docNumberHeader;
    }

    public void setDocNumberHeader(String docNumberHeader) {
        this.docNumberHeader = docNumberHeader;
    }

    public String getMdCodeHeader() {
        return mdCodeHeader;
    }

    public void setMdCodeHeader(String mdCodeHeader) {
        this.mdCodeHeader = mdCodeHeader;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public String getShiptoMdCode() {
        return shiptoMdCode;
    }

    public void setShiptoMdCode(String shiptoMdCode) {
        this.shiptoMdCode = shiptoMdCode;
    }

    public Integer getUnitGroupId() {
        return unitGroupId;
    }

    public void setUnitGroupId(Integer unitGroupId) {
        this.unitGroupId = unitGroupId;
    }

    public String getMcTypeId() {
        return mcTypeId;
    }

    public void setMcTypeId(String mcTypeId) {
        this.mcTypeId = mcTypeId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public LocalDate getDateMaintain() {
        return dateMaintain;
    }

    public void setDateMaintain(LocalDate dateMaintain) {
        this.dateMaintain = dateMaintain;
    }
}
