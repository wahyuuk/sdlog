package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AhmsdlogHdrMngQqDosPk implements Serializable {

    @Column(name = "RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumber;

    @Column(name = "RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "MSDUNTGP_NID", nullable = false)
    private Integer unitGroupId;

    @Column(name = "DSDMCT_RSDMCT_VMCTYPEID", nullable = false)
    private String mcTypeId;

    @Column(name = "DSDMCT_VCOLORID", nullable = false)
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
