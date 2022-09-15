package co.id.ahm.sdlog.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Log018VoPembukaanDoData {

    private LocalDate date;
    private List<Map<String, Object>> data;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
