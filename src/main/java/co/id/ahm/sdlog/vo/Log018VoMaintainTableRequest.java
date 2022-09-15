package co.id.ahm.sdlog.vo;

import java.time.LocalDate;

public class Log018VoMaintainTableRequest {

    private String docNumber;
    private String mdCode;
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

    public LocalDate getDateMaintain() {
        return dateMaintain;
    }

    public void setDateMaintain(LocalDate dateMaintain) {
        this.dateMaintain = dateMaintain;
    }
}
