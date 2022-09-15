package co.id.ahm.sdlog.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class AhmsdlogHdrMntcQqsPk implements Serializable {

    @Column(name = "RSDMNGQQ_RSDSHPQQ_VDOCNOSHPQQ", nullable = false)
    private String docNumber;

    @Column(name = "RSDMNGQQ_RSDSHPQQ_VMDCODE", nullable = false)
    private String mdCode;

    @Column(name = "RSDMNGQQ_MSDUNTGP_NID", nullable = false)
    private Integer unitGroupId;

    @Column(name = "RSDMNGQQ_DSDMCT_RSDMCT_VMCTYPEID", nullable = false)
    private String mcTypeId;

    @Column(name = "RSDMNGQQ_DSDMCT_VCOLORID", nullable = false)
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
