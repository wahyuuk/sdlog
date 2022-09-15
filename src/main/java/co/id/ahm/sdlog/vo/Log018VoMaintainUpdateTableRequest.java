package co.id.ahm.sdlog.vo;

import java.time.LocalDate;

public class Log018VoMaintainUpdateTableRequest {

    private String docNumber;
    private String mdCode;
    private String mcTypeId;
    private String colorId;
    private String shipto;
    private String shiptoMd;
    private LocalDate dateMaintain;
    private Integer unitGroup;
    private Integer doVinOld;
    private Integer doVinNew;

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

    public Integer getUnitGroup() {
        return unitGroup;
    }

    public void setUnitGroup(Integer unitGroup) {
        this.unitGroup = unitGroup;
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
}
