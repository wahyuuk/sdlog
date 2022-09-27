package co.id.ahm.sdlog.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Log018VoHistoryResponse {
    private List<Map<String, Object>> rows;
    private Set<String> alias;
    private Set<String> weeks;
    private Set<String> desc;

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    public Set<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(Set<String> weeks) {
        this.weeks = weeks;
    }

    public Set<String> getDesc() {
        return desc;
    }

    public void setDesc(Set<String> desc) {
        this.desc = desc;
    }
}
