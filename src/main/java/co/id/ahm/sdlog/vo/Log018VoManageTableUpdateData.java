package co.id.ahm.sdlog.vo;

public class Log018VoManageTableUpdateData {

    private String shipto;
    private String mcTypeId;
    private String colorId;
    private Integer unitGroupId;
    private Integer vinOld;
    private Integer vinNew;

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public Integer getVinOld() {
        return vinOld;
    }

    public void setVinOld(Integer vinOld) {
        this.vinOld = vinOld;
    }

    public Integer getVinNew() {
        return vinNew;
    }

    public void setVinNew(Integer vinNew) {
        this.vinNew = vinNew;
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

    public Integer getUnitGroupId() {
        return unitGroupId;
    }

    public void setUnitGroupId(Integer unitGroupId) {
        this.unitGroupId = unitGroupId;
    }
}
