package co.id.ahm.sdlog.vo;

import java.time.LocalDate;
import java.util.Date;

public class Log018VoMaintainUpdateRequest {
    private String colorId;
    private String dateMaintain;
    private Integer doVinNew;
    private Integer doVinOld;
    private String docNumber;
    private Integer hVinNew;
    private Integer hVinOld;
    private String mcTypeId;
    private String mdCode;
    private String shipto;
    private String shiptoMd;
    private Integer unitGroupId;

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getDateMaintain() {
        return dateMaintain;
    }

    public void setDateMaintain(String dateMaintain) {
        this.dateMaintain = dateMaintain;
    }

    public Integer getDoVinNew() {
        return doVinNew;
    }

    public void setDoVinNew(Integer doVinNew) {
        this.doVinNew = doVinNew;
    }

    public Integer getDoVinOld() {
        return doVinOld;
    }

    public void setDoVinOld(Integer doVinOld) {
        this.doVinOld = doVinOld;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer gethVinNew() {
        return hVinNew;
    }

    public void sethVinNew(Integer hVinNew) {
        this.hVinNew = hVinNew;
    }

    public Integer gethVinOld() {
        return hVinOld;
    }

    public void sethVinOld(Integer hVinOld) {
        this.hVinOld = hVinOld;
    }

    public String getMcTypeId() {
        return mcTypeId;
    }

    public void setMcTypeId(String mcTypeId) {
        this.mcTypeId = mcTypeId;
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

    public Integer getUnitGroupId() {
        return unitGroupId;
    }

    public void setUnitGroupId(Integer unitGroupId) {
        this.unitGroupId = unitGroupId;
    }
}
