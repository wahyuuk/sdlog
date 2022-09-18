package co.id.ahm.sdlog.dao;

import co.id.ahm.sdlog.vo.Log018VoPembukaanDoData;
import co.id.ahm.sdlog.vo.Log018VoPembukaanDoResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AhmsdlogHdrMngPldosRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Map<String, Object> getPlanDo(String docNumber, String mdCode,
                                                 Integer month, Integer year, List<String> shipto) {
        Map<String, Object> doResponse = new HashMap<>();

        if(getContPlanDo(docNumber, mdCode) == 0) {
            setResponseData(doResponse, month, year, shipto);

            return doResponse;
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sqlQuery = getPlanDoQuery();
        Query query = session.createNativeQuery(sqlQuery)
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdCode)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> data = query.getResultList();

        setResponsePlanDoData(data, doResponse);

        return doResponse;
    }

    private void setResponsePlanDoData(List<Map<String, Object>> res, Map<String, Object> doResponse) {
        Set<Map<String, Object>> listQq = new HashSet<>();
        Map<String, Object> dataTemp = new HashMap<>();

        List<Map<String, Object>> dataList = new ArrayList<>();

        Integer rows = 0;
        for(Map<String, Object> data : res) {
            Map<String, Object> qq = new HashMap<>();
            String date = new SimpleDateFormat("yyyy-MM-dd")
                    .format((Date) data.get("dmntn"));

            setShiptoQqData(data, qq);

            listQq.add(qq);

            if(rows == 0) {
                dataTemp.put("date", date);
                dataTemp.put((String) qq.get("shipto"), data.get("vstatshpqq"));

                rows++;
                continue;
            } else if(rows == res.size() - 1) {
                dataList.add(dataTemp);
            }

            String dateTemp = (String) dataTemp.get("date");

            if (!dateTemp.equals(date)) {
                dataList.add(dataTemp);

                dataTemp = new HashMap<>();
                dataTemp.put("date", date);
            }

            dataTemp.put((String) qq.get("shipto"), data.get("vstatshpqq"));

            rows++;
        }

        doResponse.put("requestDo", listQq);
        doResponse.put("rows", dataList);
    }

    private void setShiptoQqData(Map<String, Object> data, Map<String, Object> qq) {
        qq.put("shipto", data.get("VSHIPTO"));
        qq.put("mdCode", data.get("VMDCODE"));
        qq.put("city", data.get("VCITY"));
        qq.put("shiptoMd", data.get("VSHIPTOMD"));
        qq.put("isRegular", data.get("VFLAGREG"));
    }

    private void setResponseData(Map<String, Object> doResponse, Integer month,
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

        doResponse.put("requestDo", null);
        doResponse.put("rows", rows);
    }

    private Integer getContPlanDo(String docNumber, String mdCode) {
        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery(getCountPlanDoQuery())
                .setParameter("docNumber", docNumber)
                .setParameter("mdcode", mdCode);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private String getPlanDoQuery() {
        return "select q.VSHIPTO, q.VMDCODE, q.VCITY, concat(q.VSHIPTO, q.VMDCODE) as VSHIPTOMD,\n" +
                "Q.VFLAGREG, a.* from ahmsdlog_hdrmngpldos a, ahmsdlog_mstqqs q\n" +
                "where\n" +
                "      q.VSHIPTO = a.msdqq_vshipto\n" +
                "      and q.VMDCODE = a.msdqq_vmdcode\n" +
                "      and a.rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "      and a.rsdshpqq_vmdcode = :mdcode\n" +
                "order by a.dmntn";
    }

    private String getCountPlanDoQuery() {
        return "select count(*)\n" +
                "from ahmsdlog_hdrmngpldos a\n" +
                "where a.rsdshpqq_vdocnoshpqq = :docNumber\n" +
                "and a.rsdshpqq_vmdcode = :mdcode";
    }
}
