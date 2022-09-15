package co.id.ahm.sdlog.dao;

import co.id.ahm.sdlog.vo.Log018VoPembukaanDoData;
import co.id.ahm.sdlog.vo.Log018VoPembukaanDoResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AhmsdlogHdrMngPldosRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Log018VoPembukaanDoResponse getPlanDo(String docNumber, String mdCode,
                                                 Integer month, Integer year, List<String> shipto) {
        Log018VoPembukaanDoResponse doResponse = new Log018VoPembukaanDoResponse();

        if(getContPlanDo(docNumber, mdCode) == 0) {
            setResponseData(doResponse, month, year, shipto);

            return doResponse;
        }

        return null;
    }

    private void setResponseData(Log018VoPembukaanDoResponse doResponse, Integer month,
                                 Integer year, List<String> shipto) {

        LocalDate date = LocalDate.of(year, month, 1);
        LocalDate endDate = date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
        Integer lastDate = endDate.getDayOfMonth();
        List<Log018VoPembukaanDoData> rows = new ArrayList<>();

        for (int i = 1; i <= lastDate; i++) {
            Log018VoPembukaanDoData row = new Log018VoPembukaanDoData();
            LocalDate dateValue = LocalDate.of(year, month, i);
            row.setDate(dateValue);

            List<Map<String, Object>> data = new ArrayList<>();

            shipto.forEach(d -> {
                Map<String, Object> dataValue = new HashMap<>();

                dataValue.put("shipto", d);
                dataValue.put("status", false);
                data.add(dataValue);
            });

            row.setData(data);
            rows.add(row);
        }

        doResponse.setRequestDo(shipto);
        doResponse.setRows(rows);
    }

    private Integer getContPlanDo(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery(getCountPlanDoQuery())
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdCode);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private String getPlanDoQuery() {
        return "select a.*\n" +
                "from ahmsdlog_hdrmngpldos a\n" +
                "where a.rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "and a.rsdshpqq_vmdcode = :mdcode";
    }

    private String getCountPlanDoQuery() {
        return "select count(*)\n" +
                "from ahmsdlog_hdrmngpldos a\n" +
                "where a.rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "and a.rsdshpqq_vmdcode = :mdcode";
    }
}
