package co.id.ahm.sdlog.vo;

import java.util.List;

public class Log018VoPembukaanDoResponse {

    private List<String> requestDo;
    private List<Log018VoPembukaanDoData> rows;

    public List<String> getRequestDo() {
        return requestDo;
    }

    public void setRequestDo(List<String> requestDo) {
        this.requestDo = requestDo;
    }

    public List<Log018VoPembukaanDoData> getRows() {
        return rows;
    }

    public void setRows(List<Log018VoPembukaanDoData> rows) {
        this.rows = rows;
    }
}
