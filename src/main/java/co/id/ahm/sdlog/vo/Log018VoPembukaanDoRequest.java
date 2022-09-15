package co.id.ahm.sdlog.vo;

import java.util.List;

public class Log018VoPembukaanDoRequest extends Log018VoPembukaanDoResponse {

    private String docNumber;
    private String mdCode;

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
}
