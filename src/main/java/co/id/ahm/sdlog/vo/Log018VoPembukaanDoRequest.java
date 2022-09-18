package co.id.ahm.sdlog.vo;

import java.util.List;
import java.util.Map;

public class Log018VoPembukaanDoRequest {

    private String docNumber;
    private String mdCode;
    private List<Map<String, Object>> requestDo;
    private List<Map<String, Object>> rows;

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

    public List<Map<String, Object>> getRequestDo() {
        return requestDo;
    }

    public void setRequestDo(List<Map<String, Object>> requestDo) {
        this.requestDo = requestDo;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
