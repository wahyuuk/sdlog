package co.id.ahm.sdlog.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Log018VoPembukaanDoResponse {

    private Set<Map<String, Object>> requestDo;
    private List<Log018VoPembukaanDoData> rows;

    public Set<Map<String, Object>> getRequestDo() {
        return requestDo;
    }

    public void setRequestDo(Set<Map<String, Object>> requestDo) {
        this.requestDo = requestDo;
    }

    public List<Log018VoPembukaanDoData> getRows() {
        return rows;
    }

    public void setRows(List<Log018VoPembukaanDoData> rows) {
        this.rows = rows;
    }
}
