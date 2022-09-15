package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AhmsdlogDtlMngQqDosPk implements Serializable {

    @Column(name = "DSDSHPQQ_RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumber;

    @Column(name = "DSDSHPQQ_RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumberHeader;

    @Column(name = "RSDMNGQQ_RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCodeHeader;

    @Column(name = "DSDSHPQQ_MSDQQ_VSHIPTO", nullable = false)
    private String shipto;

    @Column(name = "DSDSHPQQ_MSDQQ_VMDCODE", nullable = false)
    private String shiptoMdCode;

    @Column(name = "RSDMNGQQ_MSDUNTGP_NID", nullable = false)
    private Integer unitGroupId;

    @Column(name = "RSDMNGQQ_DSDMCT_RSDMCT_VMCTYPEID", nullable = false)
    private String mcTypeId;

    @Column(name = "RSDMNGQQ_DSDMCT_VCOLORID", nullable = false)
    private String colorId;

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
}
